package com.haoshuai.intelligentcommunity.service;

import com.haoshuai.intelligentcommunity.entity.Chats;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-22
 */
public interface IChatsService extends IService<Chats> {
        List<Chats> selectAll(Chats chats);
}
