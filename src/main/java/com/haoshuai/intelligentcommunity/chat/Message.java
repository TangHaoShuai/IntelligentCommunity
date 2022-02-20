package com.haoshuai.intelligentcommunity.chat;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * WebSocket 聊天消息类
 */

public class Message {

    public static final String ENTER = "ENTER"; //进入
    public static final String SPEAK = "SPEAK"; //发言
    public static final String QUIT = "QUIT";  //退出

    private String type;//消息类型

    private String s_name; //发送人
    private String t_name; //接受人
    private String msg; //发送消息

    private int onlineCount; //在线用户数

    public Message(String type, String s_name, String t_name, String msg, int onlineCount) {
        this.type = type;
        this.s_name = s_name;
        this.t_name = t_name;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }

    public Message() {
    }

    public static String jsonStr(String type, String s_name,String t_name, String msg, int onlineTotal) {
        return JSON.toJSONString(new Message(type, s_name,t_name, msg, onlineTotal));
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", s_name='" + s_name + '\'' +
                ", t_name='" + t_name + '\'' +
                ", msg='" + msg + '\'' +
                ", onlineCount=" + onlineCount +
                '}';
    }
}
