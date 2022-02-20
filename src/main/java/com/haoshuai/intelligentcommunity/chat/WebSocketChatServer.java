package com.haoshuai.intelligentcommunity.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Component
@ServerEndpoint("/chat/{s_name}")
public class WebSocketChatServer {

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    /**
     * 如果用户不在线 就先把消存起来 等用户上线了 在推送过去
     */
    private static List<Message> tem_data = new ArrayList<>();

    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(@PathParam("s_name") String s_name, Session session) {
//        String ID = session.getRequestURI().toString().split("ID=")[1];
        onlineSessions.put(s_name, session);
        sendMessageToAll(Message.jsonStr(Message.ENTER, s_name, "", s_name + "链接成功！当前在线人数:" + onlineSessions.size(), onlineSessions.size()));
        System.out.println(s_name + "链接成功！当前在线人数:" + onlineSessions.size());
        //上线以后 先去看看 有没有未读消息 然后推送给用户
        tem_data.forEach(message -> {
            if (message.getT_name().equals(s_name)) {
                sendMessageToUser(message);
            }
        });
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Message message = JSON.parseObject(jsonStr, Message.class);
        message.setOnlineCount(onlineSessions.size());
        if (!message.getT_name().equals("admin")) {
            if (message.getT_name() != null) {
                sendMessageToUser(message);
            } else {
                tem_data.add(message);
                System.out.println(message.getT_name() + "不在线");
            }
        } else {
            sendMessageToAll(Message.jsonStr(Message.SPEAK, message.getS_name(), message.getT_name(), message.getMsg(), onlineSessions.size()));
        }
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session) {
        onlineSessions.remove(session.getId());
        sendMessageToAll(Message.jsonStr(Message.QUIT, "", "", "", onlineSessions.size()));
        System.out.println("关闭");
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(String msg) {
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
                System.out.println("广播消息" + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 对指定ID 发送消息
     *
     * @param message
     * @return
     */
    public static boolean sendMessageToUser(Message message) {
        if (message.getT_name() == null) return false;
        Session session = onlineSessions.get(message.getT_name());
        if (session == null) return false;
        if (!session.isOpen()) return false;
        try {
            session.getBasicRemote().sendText(message.toString());
            System.out.println("广播消息" + message.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
