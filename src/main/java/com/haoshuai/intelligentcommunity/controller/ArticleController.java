package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.*;
import com.haoshuai.intelligentcommunity.entity.model.ArticleModel;
import com.haoshuai.intelligentcommunity.entity.model.CommentModel;
import com.haoshuai.intelligentcommunity.entity.model.PraiseModel;
import com.haoshuai.intelligentcommunity.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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

    @Autowired
    IPraiseService iPraiseService;

    @Autowired
    ICommentService iCommentService;

    @PostMapping("deleteArticle")
    public String deleteArticle(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String articleid = map.get("articleid");
        if (id != null && !id.equals("") && articleid != null && !articleid.equals("")) {
//            Article
            QueryWrapper<Article> articleUpdateWrapper = new QueryWrapper<>();
            articleUpdateWrapper.eq("imgid", id);
            iArticleService.remove(articleUpdateWrapper);
//            ImgList
            QueryWrapper<ImgList> imgListQueryWrapper = new QueryWrapper<>();
            imgListQueryWrapper.eq("imgid", id);
            List<ImgList> imgLists = iImgListService.list(imgListQueryWrapper);
            List<File> fileList = new ArrayList<>();
            if (imgLists != null && imgLists.size() > 0) {
                iImgListService.remove(imgListQueryWrapper);
                for (ImgList imgL : imgLists) {
                    String path = "D:\\TangHaoShuai\\Pictures\\vue_img\\article_img\\" + imgL.getImgUrl();
                    File file = new File(path);
                    fileList.add(file);
                }
            }
            for (File file : fileList) {
                if (file.exists()) {
                    try {
                        file.delete();
                    } catch (Exception e) {
                        System.out.println("文件删除错误！" + e.getMessage());
                    }
                }
            }
//            Praise
            QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
            praiseQueryWrapper.eq("articleid", articleid);
            iPraiseService.remove(praiseQueryWrapper);
//            Comment
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("articleid", articleid);
            iCommentService.remove(commentQueryWrapper);
        }
        return "200";
    }

    @PostMapping("getList")
    public List<ArticleModel> getList() {
        List<ArticleModel> articleModels = new ArrayList<>();
        List<Article> articles = iArticleService.list();
        for (int i = 0; i < articles.size(); i++) {
            ArticleModel articleModel = new ArticleModel();
            //        Article
            articleModel.setUuid(articles.get(i).getUuid());
            articleModel.setUserid(articles.get(i).getUserid());
            articleModel.setContent(articles.get(i).getContent());
            articleModel.setImgid(articles.get(i).getImgid());
            articleModel.setDate(articles.get(i).getDate());
//            user
            String phone = articles.get(i).getUserid();
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", phone);
            User user = iUserService.getOne(wrapper);
            articleModel.setUser(user);
//            ImgList
            QueryWrapper<ImgList> imgListQueryWrapper = new QueryWrapper<>();
            imgListQueryWrapper.eq("imgid", articles.get(i).getImgid());
            List<ImgList> imgLists = iImgListService.list(imgListQueryWrapper);
            articleModel.setImgLists(imgLists);
            // Praise
            QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
            praiseQueryWrapper.eq("articleid", articles.get(i).getUuid());
            List<Praise> praiseList = iPraiseService.list(praiseQueryWrapper);
            List<PraiseModel> praiseModels = new ArrayList<>();
            for (Praise p : praiseList) {
                PraiseModel praiseModel = new PraiseModel();
                praiseModel.setArticleid(p.getArticleid());
                praiseModel.setUuid(p.getUuid());
                praiseModel.setDate(p.getDate());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("phone", p.getUserid());
                User user1 = iUserService.getOne(userQueryWrapper);
                praiseModel.setUser(user1);
                praiseModels.add(praiseModel);
            }
            articleModel.setPraiseList(praiseModels);
//        Comment
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("articleid", articles.get(i).getUuid());
            List<Comment> commentList = iCommentService.list(commentQueryWrapper);
            //beg 添加点赞计数和评论计数
            //每次查询readCount++
            UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
            articleUpdateWrapper.eq("uuid", articles.get(i).getUuid());
//            if (articles.get(i).getReadCount() == 0) {
//                articles.get(i).setReadCount(1);
//            } else {
//                articles.get(i).setReadCount(articles.get(i).getReadCount() + 1);
//            }
            articles.get(i).setCommentCount(commentList.size());//添加评论计数
            articles.get(i).setPraiseCount(praiseList.size());//添加点赞计数
            iArticleService.update(articles.get(i), articleUpdateWrapper);//写进数据库
            articleModel.setCommentCount(commentList.size());
            articleModel.setReadCount(articles.get(i).getReadCount());
            articleModel.setPraiseCount(praiseList.size());
            //end
            List<CommentModel> commentModels = new ArrayList<>();
            for (Comment c : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setArticleid(c.getArticleid());
                commentModel.setMessage(c.getMessage());
                commentModel.setDate(c.getDate());
                commentModel.setUserid(c.getUserid());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("phone", c.getUserid());
                User user1 = iUserService.getOne(userQueryWrapper);
                commentModel.setUser(user1);
                commentModels.add(commentModel);
            }
            //排序
            commentModels.sort(Comparator.comparing(CommentModel::getDate));
            Collections.reverse(commentModels);
            articleModel.setCommentList(commentModels);
            articleModels.add(articleModel);
        }
        //排序
        articleModels.sort(Comparator.comparing(ArticleModel::getDate));
        Collections.reverse(articleModels);
        return articleModels;
    }

    @PostMapping("getOneArticle")
    public ArticleModel getOneArticle(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        ArticleModel articleModel = new ArticleModel();
        //Article
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("uuid", id);
        Article article = iArticleService.getOne(articleQueryWrapper);
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
        // Praise点赞集合
        QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
        praiseQueryWrapper.eq("articleid", article.getUuid());
        List<Praise> praiseList = iPraiseService.list(praiseQueryWrapper);
        List<PraiseModel> praiseModels = new ArrayList<>();
        for (Praise p : praiseList) {
            PraiseModel praiseModel = new PraiseModel();
            praiseModel.setArticleid(p.getArticleid());
            praiseModel.setUuid(p.getUuid());
            praiseModel.setDate(p.getDate());
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("phone", p.getUserid());
            User user1 = iUserService.getOne(userQueryWrapper);
            praiseModel.setUser(user1);
            praiseModels.add(praiseModel);
        }
        articleModel.setPraiseList(praiseModels);
        //Comment 评论集合
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("articleid", article.getUuid());
        List<Comment> commentList = iCommentService.list(commentQueryWrapper);
        //beg 添加点赞计数和评论计数和阅读计数
        //每次查询readCount++
        UpdateWrapper<Article> articleUpdateWrapper = new UpdateWrapper<>();
        articleUpdateWrapper.eq("uuid", id);
        if (article.getReadCount() == 0) {
            article.setReadCount(1);
        } else {
            article.setReadCount(article.getReadCount() + 1);
        }
        article.setCommentCount(commentList.size());//添加评论计数
        article.setPraiseCount(praiseList.size());//添加点赞计数
        iArticleService.update(article, articleUpdateWrapper);//写进数据库
        articleModel.setCommentCount(praiseList.size());
        articleModel.setReadCount(praiseList.size());
        articleModel.setPraiseCount(praiseList.size());
        //end
        List<CommentModel> commentModels = new ArrayList<>();
        for (Comment c : commentList) {
            CommentModel commentModel = new CommentModel();
            commentModel.setArticleid(c.getArticleid());
            commentModel.setMessage(c.getMessage());
            commentModel.setDate(c.getDate());
            commentModel.setUserid(c.getUserid());
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("phone", c.getUserid());
            User user1 = iUserService.getOne(userQueryWrapper);
            commentModel.setUser(user1);
            commentModels.add(commentModel);
        }

        //排序
        commentModels.sort(Comparator.comparing(CommentModel::getDate));
        Collections.reverse(commentModels);
        articleModel.setCommentList(commentModels);

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
