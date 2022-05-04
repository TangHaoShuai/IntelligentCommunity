package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.News;
import com.haoshuai.intelligentcommunity.entity.PageEntity;
import com.haoshuai.intelligentcommunity.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/news")

public class NewsController {

    @Autowired
    private INewsService iNewsService;

    @GetMapping("/getNews")
    public List<News> getNews() {
        return iNewsService.list();
    }

    @GetMapping("/findNews")
    public List<News> findNews() {
        return iNewsService.findNews();
    }


    /**
     * 根据url 删除新闻
     * @param news
     * @return
     */
    @PostMapping("deleteNews")
    public boolean deleteNews(@RequestBody News news) {
        if (news.getUrl() != null && news.getUrl() != "") {
            QueryWrapper<News> newsQueryWrapper = new QueryWrapper<>();
            newsQueryWrapper.eq("url", news.getUrl());
            return iNewsService.remove(newsQueryWrapper);
        }
        return false;
    }

    /**
     * 新闻分页查询
     *
     * @param map
     * @return
     */
    @PostMapping("/getNewsPage")
    @ResponseBody
    public PageEntity getNewsPage(@RequestBody Map<String, String> map) {
        long current = Long.parseLong(map.get("current"));
        long size = Long.parseLong(map.get("size"));
        String title = map.get("title");
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(title), "title", title);
        Page<News> page = new Page<>(current, size);
        iNewsService.page(page, queryWrapper);
        PageEntity pageEntity = new PageEntity(page);
        return pageEntity;
    }

    @GetMapping("/selectPage")
    public List<News> selectPage() {
        IPage<News> page = new Page<>(1, 5);
        iNewsService.page(page, null);
        List<News> news = page.getRecords();
        return news;
    }
}
