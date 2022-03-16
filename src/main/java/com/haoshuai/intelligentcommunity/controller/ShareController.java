package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.PageEntity;
import com.haoshuai.intelligentcommunity.entity.Share;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.qr_code.QRCodeUtil;
import com.haoshuai.intelligentcommunity.service.IShareService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 共享
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/share")
public class ShareController {
    @Autowired
    private IShareService iShareService;

    private String destPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\ShareCR\\";// 生成的二维码的路径及名称
    private String imgPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\Share\\";// 嵌入二维码的图片路径

    @PostMapping("getUserShareList")
    public List<Share> getUserShareList(@RequestBody Share share){
        if ((share.getUserid() != ""&& share.getUserid() != null)){
            QueryWrapper<Share> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotEmpty(share.getUserid()),"userid",share.getUserid());
            return iShareService.list(queryWrapper);
        }
        return null;
    }

    @PostMapping("getShare")
    public Share getShare(@RequestBody Share share) {
        if (share.getUuid() != null && share.getUuid() != null) {
            QueryWrapper<Share> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", share.getUuid());
            return iShareService.getOne(queryWrapper);
        }
        return null;
    }

    @PostMapping("upShare")
    public Map<String, String> upShare(@RequestBody Share share) {
        Map<String, String> map = new HashMap<>();
        if (share.getName() != null && share.getName() != "" && share.getDescription() != null && share.getDescription() != "") {
            UpdateWrapper<Share> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", share.getUuid());
            //beg更新二维码
            try {
                String tempPath = this.destPath + share.getQrimg();
                File file = new File(tempPath);
                if (file.exists()) {
                    file.delete();
                }
                //生成二维码
                String imgPath = this.imgPath + share.getImg(); //设置嵌入图片
                String destPath = this.destPath + share.getQrimg();//组成输入路径
//                JSONObject jsonObject = JSONObject.fromObject(share);
//                String qrCodeContent =jsonObject.toString(); //二维码的信息
                String qrCodeContent = share.getUuid();
                QRCodeUtil.encode(qrCodeContent, imgPath, destPath, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //end
            if (iShareService.update(share, updateWrapper)) {
                map.put("state", "succeed");
                map.put("imgId", share.getImg());
                map.put("message", share.getUuid());//返回uuid给前端
            }
        } else {
            map.put("state", "err");
            map.put("message", "name or description is null");//返回uuid给前端
        }
        return map;
    }

    @PostMapping("addShare")
    public Map<String, String> addShare(@RequestBody Share share) {
        Map<String, String> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        if (share.getName() != null && share.getName() != "" && share.getDescription() != null && share.getDescription() != "") {
            share.setUuid(uuid);

//            JSONObject jsonObject = JSONObject.fromObject(share);
//            String qrCodeContent = jsonObject.toString(); //二维码的信息
            String qrCodeContent = share.getUuid();
            String imgPath = this.imgPath + "qrcoe.jpeg"; //设置默认嵌入图片
            Long startTs = System.currentTimeMillis();//时间戳
            String qrImgName = "tsd" + startTs + ".jpg";
            String destPath = this.destPath + qrImgName;//组成输入路径
            share.setQrimg(qrImgName);//写入默认二维码
            try {
                //生成二维码
                QRCodeUtil.encode(qrCodeContent, imgPath, destPath, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (iShareService.save(share)) {
                map.put("state", "succeed");
                map.put("message", uuid);//返回uuid给前端
            }
        } else {
            map.put("state", "err");
            map.put("message", "name or description is null");//返回uuid给前端
        }
        return map;
    }

    @PostMapping("selectPage")
    public PageEntity selectPage(@RequestBody Map<String, String> map) {
        long current = Long.parseLong(map.get("current"));
        long size = Long.parseLong(map.get("size"));
        String uuid = map.get("uuid");
        String name = map.get("name");
        QueryWrapper<Share> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(uuid), "uuid", uuid);
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);
        Page<Share> page = new Page<>(current, size);
        iShareService.page(page, queryWrapper);
        PageEntity pageEntity = new PageEntity(page);
        return pageEntity;
    }


    @PostMapping("deleteShare")
    public boolean deleteShare(@RequestBody Share share) {
        if (share.getName() != null && share.getName() != "" && share.getDescription() != null && share.getDescription() != "") {
            String tempImgPath = this.imgPath + share.getImg();
            String tempDestPath = this.destPath + share.getQrimg();
            File file = new File(tempDestPath);
            File file1 = new File(tempImgPath);
            if (file.exists()) {
                file.delete();
            }
            if (file1.exists()) {
                file1.delete();
            }
            QueryWrapper<Share> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", share.getUuid());
            return iShareService.remove(queryWrapper);
        } else {
            return false;
        }
    }

}
