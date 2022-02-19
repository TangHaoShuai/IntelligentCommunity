package com.haoshuai.intelligentcommunity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.News;
import com.haoshuai.intelligentcommunity.mapper.NewsMapper;
import com.haoshuai.intelligentcommunity.service.INewsService;
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
 * @since 2022-01-10
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> findNews() {
        return newsMapper.findNews();
    }
    @Override
    public IPage<News> selectUserPage(Page<News> page) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return newsMapper.selectPageVo(page);
    }
}
