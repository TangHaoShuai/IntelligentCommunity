package com.haoshuai.intelligentcommunity.service.impl;

import com.haoshuai.intelligentcommunity.entity.Chats;
import com.haoshuai.intelligentcommunity.mapper.ChatsMapper;
import com.haoshuai.intelligentcommunity.service.IChatsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-22
 */
@Service
public class ChatsServiceImpl extends ServiceImpl<ChatsMapper, Chats> implements IChatsService {
    @Autowired
    private ChatsMapper chatsMapper;

    @Override
    public List<Chats> selectAll(Chats chats) {
        return chatsMapper.selectAll(chats);
    }
}
