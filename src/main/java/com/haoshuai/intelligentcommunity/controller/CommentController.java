package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.Comment;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.entity.model.CommentModel;
import com.haoshuai.intelligentcommunity.entity.model.CommunityServicesModel;
import com.haoshuai.intelligentcommunity.service.ICommentService;
import com.haoshuai.intelligentcommunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private IUserService iUserService;

    /**
     * 获取评论记录集合
     * @param comment
     * @return
     */
    @PostMapping("getComments")
    public List<CommentModel>getComments(@RequestBody Comment comment){
        List<CommentModel> commentModels = new ArrayList<>();
        if (comment.getArticleid() != null && comment.getArticleid() !="" && comment.getUserid() != null && comment.getUserid() != ""){
            QueryWrapper<Comment>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",comment.getUserid());
            queryWrapper.eq("articleid",comment.getArticleid());
            List<Comment>commentList= iCommentService.list(queryWrapper);

            for (Comment c : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setUuid(c.getUuid());
                commentModel.setArticleid(c.getArticleid());
                commentModel.setMessage(c.getMessage());
                commentModel.setDate(c.getDate());
                commentModel.setUserid(c.getUserid());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("phone", c.getUserid());
                User user1 = iUserService.getOne(userQueryWrapper);
                commentModel.setUser(user1);
                commentModels.add(commentModel);
            }
            return commentModels;
        }
        return null;
    }

    /**
     * 删除评论记录
     * @param comment
     * @return
     */
    @PostMapping("deleteComment")
    public boolean deleteComment(@RequestBody Comment comment) {
        if (comment.getUuid() != null && comment.getUuid() != "") {
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", comment.getUuid());
            return iCommentService.remove(queryWrapper);
        }
        return false;
    }

    /**
     * 添加评论记录
     * @param comment
     * @return
     */
    @PostMapping("addComment")
    public boolean addComment(@RequestBody Comment comment) {
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
