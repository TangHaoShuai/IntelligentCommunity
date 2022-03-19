package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.AdminUser;
import com.haoshuai.intelligentcommunity.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/admin-user")
public class AdminUserController {

    @Autowired
    private IAdminUserService iAdminUserService;

    @PostMapping("adminLogin")
    public boolean adminLogin(@RequestBody AdminUser adminUser) {
        if (adminUser.getUsername() == null || adminUser.getUsername() == "" || adminUser.getPassword() == null || adminUser.getPassword() == "") {
            return false;
        } else {
            QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("password", adminUser.getPassword());
            queryWrapper.eq("username", adminUser.getUsername());
            AdminUser adminUser1 = iAdminUserService.getOne(queryWrapper);
            if (adminUser1 != null) {
                return true;
            } else {
                return false;
            }
        }
    }
}
