package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
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

    @PostMapping("/getNewsPage")
    @ResponseBody
    public PageEntity getNewsPage(@RequestBody Map<String, Long> map) {
        long current = map.get("current");
        long size = map.get("size");
        Page<News> page = new Page<>(current, size);
        iNewsService.selectUserPage(page);
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
