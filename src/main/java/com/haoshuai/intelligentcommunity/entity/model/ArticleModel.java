package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.Comment;
import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.entity.Praise;
import com.haoshuai.intelligentcommunity.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleModel {
    private String uuid;

    private String userid;

    private String date;

    private String content;

    private String imgid;

    private User user;

    private List<ImgList> imgLists; //图片集合

    private List<PraiseModel> praiseList; //点赞集合

    private List<CommentModel> commentList;//评论集合
}
