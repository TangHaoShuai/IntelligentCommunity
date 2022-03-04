package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.Article;
import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.service.IImgListService;
import com.haoshuai.intelligentcommunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
public class FileController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IImgListService iImgListService;

    @GetMapping(value = "/file")
    public String file() {
        return "file";
    }

    @PostMapping(value = "/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(value = "file") MultipartFile file,@RequestParam("id")String id, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\"; // 上传后的路径
        fileName = id + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
            userWrapper.eq("phone",id);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("phone",id);
            User user = iUserService.getOne(queryWrapper);
            user.setImage(fileName);
            iUserService.update(user,userWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filename = "/TangHaoShuai/" + fileName;
        model.addAttribute("filename", filename);
        return "succeed";
    }
    @PostMapping(value = "/imgFileUpload")
    @ResponseBody
    public String imgFileUpload(@RequestParam(value = "file") MultipartFile file,@RequestParam("imgID")String imgID, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\article_img\\"; // 上传后的路径
        fileName = uuid+ suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            ImgList imgList = new ImgList();
            imgList.setUuid(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            imgList.setImgid(imgID); // 对应文章里面的图片ID
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            imgList.setDate(df.format(new Date()));
            imgList.setImgUrl(fileName); // 图片路径
            iImgListService.save(imgList);
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
//        String filename = "/TangHaoShuai/" + fileName;
//        model.addAttribute("filename", filename);
        return "succeed";
    }
}
