package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.haoshuai.intelligentcommunity.entity.Article;
import com.haoshuai.intelligentcommunity.entity.ImgList;
import com.haoshuai.intelligentcommunity.entity.Share;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.qr_code.QRCodeUtil;
import com.haoshuai.intelligentcommunity.service.IImgListService;
import com.haoshuai.intelligentcommunity.service.IShareService;
import com.haoshuai.intelligentcommunity.service.IUserService;
import net.sf.json.JSONObject;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
public class FileController {

    private String destPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\ShareCR\\";// 生成的二维码的路径及名称
//    private String imgPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\";// 嵌入二维码的图片路径

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IImgListService iImgListService;

    @Autowired
    private IShareService iShareService;

    @GetMapping(value = "/file")
    public String file() {
        return "file";
    }

    /**
     * 头像
     *
     * @param file
     * @param id      用户id
     * @param imgId   前端传过来的imgId  图片存在删除 更换新头像
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("id") String id, @RequestParam("imgId") String imgId, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\"; // 上传后的路径
        Long startTs = System.currentTimeMillis();//时间戳
        fileName = id + startTs + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        String tempPath = filePath + imgId;
        File file1 = new File(tempPath);
        if (file1.exists()) {
            try {
                file1.delete();
            } catch (Exception e) {
                System.out.println("文件删除错误！" + e.getMessage());
            }
        }

        try {
            file.transferTo(dest);
            UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
            userWrapper.eq("phone", id);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("phone", id);
            User user = iUserService.getOne(queryWrapper);
            user.setImage(fileName);
            iUserService.update(user, userWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filename = "/TangHaoShuai/" + fileName;
        model.addAttribute("filename", filename);
        return "succeed";
    }

    /**
     * 文章图片集合
     *
     * @param file    图片
     * @param imgID   对应文章那边imgID 由前端传过来
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/imgFileUpload")
    @ResponseBody
    public String imgFileUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("imgID") String imgID, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\article_img\\"; // 上传后的路径
        fileName = uuid + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
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
        return "succeed";
    }

    /**
     * @param file
     * @param shareId 共享物品ID
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/shareImgUpload")
    @ResponseBody
    public Map<String, String> shareImgUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("shareId") String shareId, @RequestParam("imgId") String imgId, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        Map<String, String> map = new HashMap<>();
        QueryWrapper<Share> shareQueryWrapper = new QueryWrapper<>();
        shareQueryWrapper.eq("uuid", shareId);
        Share share = iShareService.getOne(shareQueryWrapper);
        if (share == null) {
            map.put("state", "err");
            map.put("message", "shareId no exists");
            return map;
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\"; // 上传后的路径
        //beg 图片存在就删除
        if (imgId != null && imgId != "") {
            String tempPath = filePath + imgId;
            File file1 = new File(tempPath);
            if (file1.exists()) {
                try {
                    file1.delete();
                } catch (Exception e) {
                    System.out.println("文件删除错误！" + e.getMessage());
                }
            }
        }
        //end
        fileName = uuid + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();  //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
            share.setImg(fileName);//把图片名字写进入数据库

            //beg二维码
            //先判断原来的二维码是默认二维码 还是为空
            //删除原来的二维码
            String temp = this.destPath + share.getQrimg();
            File file1 = new File(temp);
            if (file1.exists()) {
                file1.delete();
            }
            Long startTs = System.currentTimeMillis();//时间戳
            String imgPath = filePath + fileName; //设置嵌入图片
            String qrImgName = "tsd" + startTs + ".jpg";
            String destPath = this.destPath + qrImgName;//组成输入路径
            share.setQrimg(qrImgName);
//            JSONObject jsonObject = JSONObject.fromObject(share);
//            String qrCodeContent =jsonObject.toString(); //二维码的信息
            String qrCodeContent = share.getUuid();
            //生成二维码
            QRCodeUtil.encode(qrCodeContent, imgPath, destPath, true);

            //end


            iShareService.update(share, shareQueryWrapper);//跟新数据
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "err");
            map.put("message", e.getMessage());
            return map;
        }
        map.put("state", "succeed");
        map.put("message", uuid);//返回uuid给前端
        return map;
    }
}
