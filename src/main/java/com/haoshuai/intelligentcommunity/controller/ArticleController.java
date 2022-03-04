package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.Article;
import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.entity.model.ArticleModel;
import com.haoshuai.intelligentcommunity.service.IArticleService;
import com.haoshuai.intelligentcommunity.service.IImgListService;
import com.haoshuai.intelligentcommunity.service.IUserService;
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

    @Autowired
    IUserService iUserService;

    @Autowired
    IImgListService iImgListService;

    @PostMapping("getList")
    public List<ArticleModel> getList() {
        List<ArticleModel> articleModels = new ArrayList<>();
        List<Article> articles = iArticleService.list();
        for (int i = 0; i < articles.size(); i++) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            String phone = articles.get(i).getUserid();
            wrapper.eq("phone", phone);
            User user = iUserService.getOne(wrapper);
            ArticleModel articleModel = new ArticleModel();
            articleModel.setUuid(articles.get(i).getUuid());
            articleModel.setUserid(articles.get(i).getUserid());
            articleModel.setContent(articles.get(i).getContent());
            articleModel.setImgid(articles.get(i).getImgid());
            articleModel.setDate(articles.get(i).getDate());
            articleModel.setUser(user);
            QueryWrapper<ImgList> imgListQueryWrapper = new QueryWrapper<>();
            imgListQueryWrapper.eq("imgid", articles.get(i).getImgid());
            List<ImgList> imgLists = iImgListService.list(imgListQueryWrapper);
            articleModel.setImgLists(imgLists);
            articleModels.add(articleModel);
        }
        return articleModels;
    }

    @PostMapping("getOneArticle")
    public ArticleModel getOneArticle(@RequestBody Map<String,String>map ) {
        String id = map.get("id");
        ArticleModel articleModel = new ArticleModel();
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("uuid",id);
        Article article = iArticleService.getOne(articleQueryWrapper);
//        Article
        articleModel.setDate(article.getDate());
        articleModel.setUserid(article.getUserid());
        articleModel.setContent(article.getContent());
        articleModel.setUuid(article.getUuid());
        articleModel.setImgid(article.getImgid());
//        user
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", article.getUserid());
        User user = iUserService.getOne(wrapper);
        articleModel.setUser(user);
//        img_list
        QueryWrapper<ImgList> imgListQueryWrapper = new QueryWrapper<>();
        imgListQueryWrapper.eq("imgid", article.getImgid());
        List<ImgList> imgLists = iImgListService.list(imgListQueryWrapper);
        articleModel.setImgLists(imgLists);
        return articleModel;
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
