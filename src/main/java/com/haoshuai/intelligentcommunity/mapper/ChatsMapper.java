package com.haoshuai.intelligentcommunity.mapper;

import com.haoshuai.intelligentcommunity.entity.Chats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-22
 */
@Mapper
public interface ChatsMapper extends BaseMapper<Chats> {
        List<Chats> selectAll (Chats chats);
}
