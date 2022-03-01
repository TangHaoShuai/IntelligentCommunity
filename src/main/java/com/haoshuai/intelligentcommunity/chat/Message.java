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
    public static final String NOTICE = "NOTICE"; //公告
    public static final String HEARTBEAT = "HEARTBEAT"; //心跳

    private String type;//消息类型

    private String s_name; //发送人
    private String t_name; //接受人
    private String msg; //发送消息
    private String t_date; // 发送时间

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

    public Message(String type, String s_name, String t_name, String msg, String t_date, int onlineCount) {
        this.type = type;
        this.s_name = s_name;
        this.t_name = t_name;
        this.msg = msg;
        this.t_date = t_date;
        this.onlineCount = onlineCount;
    }

    public static String jsonStr(String type, String s_name, String t_name, String msg,String t_date,  int onlineTotal) {
        return JSON.toJSONString(new Message(type, s_name, t_name, msg,t_date, onlineTotal));
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

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }
}
