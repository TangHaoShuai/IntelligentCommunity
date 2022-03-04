package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.Article;
import com.haoshuai.intelligentcommunity.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-04
 * 帖子
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService iArticleService;

    @PostMapping("getList")
    public List<Article> getList() {
        return iArticleService.list();
    }

    @PostMapping("addArticle")
    public Map<String, String> addArticle(@RequestBody Article article) {
        Map<String, String> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        article.setDate(df.format(new Date()));
        article.setUuid(uuid);
        String imgID = article.getUserid() + uuid;
        if (article.getUserid() != null && article.getUserid() != "" && article.getContent() != null
                && article.getContent() != "") {
            article.setImgid(imgID);
            map.put("message", "200");
            map.put("imgID", imgID);
            iArticleService.save(article);
        } else {
            map.put("message", "userID is null or content is null");
        }

        return map;
    }

    @PostMapping("upArticle")
    public void upArticle(@RequestBody Article article) {
        if (article.getUserid() == null) {
            System.out.println("UUID is null");
            return;
        }
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        wrapper.eq("uuid", article.getUuid());
        iArticleService.update(article, wrapper);
    }

    @PostMapping("deArticle")
    public void deArticle(@RequestBody Article article) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid", article.getUuid());
        iArticleService.remove(wrapper);
    }

}
