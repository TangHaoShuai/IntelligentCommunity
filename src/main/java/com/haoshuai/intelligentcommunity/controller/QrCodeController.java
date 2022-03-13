package com.haoshuai.intelligentcommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.qr_code.QRCodeUtil;
import com.haoshuai.intelligentcommunity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    @Autowired
    private IUserService iUserService;

    private String  destPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\qrcode\\";// 生成的二维码的路径及名称
    private String imgPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\";// 嵌入二维码的图片路径

    @PostMapping("getUserQrCode")
    public String getUserQrCode(@RequestBody User user){
        if (user.getPhone() == null || user.getPhone() == ""){
            return null;
        }
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",user.getPhone());
        user = iUserService.getOne(queryWrapper);
        String qrCodeContent = user.toString();
        String destPath = this.destPath+user.getPhone()+".jpg";
        String imgPath;
        if (user.getImage() == "" || user.getImage() == null){
            imgPath = this.imgPath+"qrcoe.jpeg"; //设置默认嵌入图片
        }else{
            imgPath = this.imgPath+user.getImage();
        }
        try {
            //生成二维码
            QRCodeUtil.encode(qrCodeContent, imgPath, destPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回图片途径
        return destPath;
    }

  /**  public static void main(String[] args) {
        // 存放在二维码中的内容
        String text = "我是小铭";
        // 嵌入二维码的图片路径
        String imgPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\18077229249.jpeg";
        // 生成的二维码的路径及名称
        String destPath = "D:\\TangHaoShuai\\Pictures\\vue_img\\qrcode\\jam.jpg";
        //生成二维码
        try {
            QRCodeUtil.encode(text, imgPath, destPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析二维码
        String str = null;
        try {
            str = QRCodeUtil.decode(destPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 打印出解析出的内容
        System.out.println(str);

    }
   **/
}
