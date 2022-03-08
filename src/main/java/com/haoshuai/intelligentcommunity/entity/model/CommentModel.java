package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.User;

public class CommentModel {

    private String articleid;

    private String userid;

    private User user;

    private String date;

    private String message;

    public CommentModel(String articleid, String userid, User user, String date, String message) {
        this.articleid = articleid;
        this.userid = userid;
        this.user = user;
        this.date = date;
        this.message = message;
    }

    public CommentModel() {
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
