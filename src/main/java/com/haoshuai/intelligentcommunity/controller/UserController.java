package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            if (users.get(i).getPhone().equals(ID)){
                users.remove(i);
            }
        }
        return users;
    }

    @PostMapping("addUser")
    public String addUser(@RequestBody User user) {
        boolean tme = false;
        if (!(user.getPhone().equals("") || user.getPassword().equals(""))) {
            tme = iUserService.save(user);
        }
        return tme ? "200" : "500";
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
