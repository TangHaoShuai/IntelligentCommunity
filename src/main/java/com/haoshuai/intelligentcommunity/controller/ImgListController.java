package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.Article;
import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.service.IImgListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/img-list")
public class ImgListController {
    @Autowired
    private IImgListService iImgListService;

    @PostMapping("getImgList")
    public List<ImgList> getList() {
        return iImgListService.list();
    }

    @PostMapping("getByUserImgList")
    public List<ImgList> getByUserImgList(@RequestBody Article article) {
        QueryWrapper<ImgList> wrapper = new QueryWrapper<>();
        wrapper.eq("imgid", article.getImgid());
        return iImgListService.list(wrapper);
    }

    @PostMapping("upImgList")
    public void upImgList(ImgList imgList){
        UpdateWrapper<ImgList> wrapper = new UpdateWrapper<>();
        wrapper.eq("uuid",imgList.getUuid());
        iImgListService.update(wrapper);
    }

    @PostMapping("deImgList")
    public void deImgList(ImgList imgList){
        QueryWrapper<ImgList> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid",imgList.getUuid());
        iImgListService.remove(wrapper);
    }

}
