package com.haoshuai.intelligentcommunity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-01-10
 */
public interface INewsService extends IService<News> {
    List<News> findNews();
    IPage<News> selectUserPage(Page<News> page);
}
