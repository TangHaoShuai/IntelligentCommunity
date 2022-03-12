package com.haoshuai.intelligentcommunity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectPageVo(Page page);
}
