package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.entity.User;

import java.util.List;

public class ArticleModel {
    private String uuid;

    private String userid;

    private String date;

    private String content;

    private String imgid;

    private User user;

    private List<ImgList> imgLists;


    public ArticleModel(String uuid, String userid, String date, String content, String imgid, User user, List<ImgList> imgLists) {
        this.uuid = uuid;
        this.userid = userid;
        this.date = date;
        this.content = content;
        this.imgid = imgid;
        this.user = user;
        this.imgLists = imgLists;
    }

    public ArticleModel() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ImgList> getImgLists() {
        return imgLists;
    }

    public void setImgLists(List<ImgList> imgLists) {
        this.imgLists = imgLists;
    }
}
