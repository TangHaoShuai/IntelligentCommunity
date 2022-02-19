package com.haoshuai.intelligentcommunity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-01-10
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {
    List<News> findNews();

    IPage<News> selectPageVo(Page page);
}
