package com.haoshuai.intelligentcommunity.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityServices implements Serializable {
    public static final String state_0 = "等待处理";
    public static final String state_1 = "正在处理";
    public static final String state_2 = "处理完成";

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String userid;

    private String message;

    private String date;

    private String state;

    public CommunityServices(String uuid, String userid, String message, String date, String state) {
        this.uuid = uuid;
        this.userid = userid;
        this.message = message;
        this.date = date;
        this.state = state;
    }

    public CommunityServices() {
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
