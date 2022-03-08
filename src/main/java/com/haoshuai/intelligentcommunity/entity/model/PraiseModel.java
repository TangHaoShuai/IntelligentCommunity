package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.User;

public class PraiseModel {

    private String articleid;

    private User user;

    private String date;

    private String uuid;

    public PraiseModel(String articleid, User user, String date, String uuid) {
        this.articleid = articleid;
        this.user = user;
        this.date = date;
        this.uuid = uuid;
    }

    public PraiseModel() {
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
