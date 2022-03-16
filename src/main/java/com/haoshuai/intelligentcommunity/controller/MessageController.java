package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.Message;
import com.haoshuai.intelligentcommunity.entity.PageEntity;
import com.haoshuai.intelligentcommunity.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 导航栏通知（广告）
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService iMessageService;

    @PostMapping("addMessage")
    public boolean addMessage(@RequestBody Message message) {
        if (message.getMessage() != null && message.getMessage() != "") {
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            message.setUuid(uuid);
            message.setDate(df.format(new Date()));
            return iMessageService.save(message);
        }
        return false;
    }

    @PostMapping("deleteMessage")
    public boolean deleteMessage(@RequestBody Message message) {
        if (message.getUuid() != null && message.getUuid() != "") {
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", message.getUuid());
            return iMessageService.remove(queryWrapper);
        }
        return false;
    }

    @PostMapping("selectPage")
    public PageEntity selectPage(@RequestBody Map<String, String> map) {
        long current = Long.parseLong(map.get("current"));
        long size = Long.parseLong(map.get("size"));
        String message = map.get("message");
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(message), "message", message);
        Page<Message> page = new Page<>(current, size);
        iMessageService.page(page, queryWrapper);
        return new PageEntity(page);
    }

    @PostMapping("updateMessage")
    public boolean updateMessage(@RequestBody Message message) {
        if (message.getUuid() != null && message.getUuid() != "") {
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", message.getUuid());
            return iMessageService.update(message, queryWrapper);
        }
        return false;
    }

}

















