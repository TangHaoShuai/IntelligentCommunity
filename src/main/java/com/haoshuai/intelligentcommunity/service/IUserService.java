package com.haoshuai.intelligentcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-20
 */
public interface IUserService extends IService<User> {

    IPage<User> selectUserPage (Page<User> userPage);

}
