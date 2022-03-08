package com.haoshuai.intelligentcommunity.controller;


import com.haoshuai.intelligentcommunity.entity.Comment;
import com.haoshuai.intelligentcommunity.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * 评论
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService iCommentService;

    @PostMapping("addComment")
    public Boolean addComment(@RequestBody Comment comment) {
        if (comment.getUserid() != "" && comment.getArticleid() != "" && comment.getMessage() != "") {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            comment.setUuid(uuid);
            comment.setDate(df.format(new Date()));
            return iCommentService.save(comment);
        }
        return false;
    }
}
