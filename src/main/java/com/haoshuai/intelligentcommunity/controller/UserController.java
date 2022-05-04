package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.*;
import com.haoshuai.intelligentcommunity.service.IArticleService;
import com.haoshuai.intelligentcommunity.service.IChatsService;
import com.haoshuai.intelligentcommunity.service.ICommentService;
import com.haoshuai.intelligentcommunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IArticleService iArticleService;

    @Autowired
    private IChatsService iChatsService;

    @Autowired
    private ICommentService iCommentService;

    @PostMapping("getUser")
    public User getUser(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!((user.getPhone().equals("") || user.getPassword().equals("")))) {
            wrapper.eq("phone", user.getPhone());
            wrapper.eq("password", user.getPassword());
        }
        return iUserService.getOne(wrapper);
    }

    @PostMapping("getUsers")
    public List<User> getUsers(@RequestBody String ID) {
        List<User> users = iUserService.list();
        for (int i = 0; i < users.size(); i++) {
            if (ID == null) return null;
            if (users.get(i).getPhone().equals(ID)) {
                users.remove(i);
            }
        }
        return users;
    }

    @PostMapping("updateUser")
    public boolean updateUser(@RequestBody User user) {
        if (user.getPhone() == "" || user.getPhone() == null) {
            return false;
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("phone", user.getPhone());
        return iUserService.update(user, userUpdateWrapper);
    }

    @PostMapping("deleteUser")
    public boolean deleteUser(@RequestBody User user) {
        if (user.getPhone() == "" || user.getPhone() == null) {
            return false;
        }
        //Article
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("userid", user.getPhone());
        List<Article> articleList = iArticleService.list(articleQueryWrapper);
        for (Article a:articleList){
            //遍历删除这个用户发的帖子里面的所有评论
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("articleid", a.getUuid());
            iCommentService.remove(commentQueryWrapper);//删除评论
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", a.getUuid());
            iArticleService.remove(queryWrapper);//在删除帖子
        }
        // 删除评论
        QueryWrapper<Chats> chatsQueryWrapper = new QueryWrapper<>();
        chatsQueryWrapper.eq("s_name", user.getPhone());
        chatsQueryWrapper.eq("t_name", user.getPhone());
        iChatsService.remove(chatsQueryWrapper);
        //最后删除这个用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        iUserService.remove(queryWrapper);
        return true;
    }

    @PostMapping("selectPage")
    public PageEntity selectPage(@RequestBody Map<String,String> map){
        long current = Long.parseLong(map.get("current"));
        long size = Long.parseLong(map.get("size"));
        String username = map.get("username");
        String phone = map.get("phone");
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(username),"username",username);
        queryWrapper.like(StringUtils.isNotEmpty(phone),"phone",phone);
        Page<User> page = new Page<>(current,size);
        iUserService.page(page,queryWrapper);
        PageEntity pageEntity = new PageEntity(page);
        return pageEntity;
    }

    @PostMapping("getUserList")
    public List<User> getUserList() {
        List<User> list = iUserService.list();
        return list;
    }

    @PostMapping("addUser")
    public boolean addUser(@RequestBody User user) {
        if (!(user.getPhone().equals("") || user.getPassword().equals(""))) {
            QueryWrapper<User>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",user.getPhone());
            User tempUser = iUserService.getOne(queryWrapper);
            if (tempUser == null ){
                return iUserService.save(user);
            }
        }
        return false;
    }

    @PostMapping("login")
    public String login(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!(user.getPhone().equals("") || user.getPassword().equals(""))) {
            wrapper.eq("phone", user.getPhone());
            wrapper.eq("password", user.getPassword());
        }
        return iUserService.getOne(wrapper) != null ? "200" : "500";
    }
}
