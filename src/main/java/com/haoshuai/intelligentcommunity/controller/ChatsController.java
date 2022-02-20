package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.Chats;
import com.haoshuai.intelligentcommunity.service.IChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-02-19
 */
@RestController
@RequestMapping("/chats")
public class ChatsController {
    @Autowired
    private IChatsService iChatsService;

    @PostMapping("/getList")
    public List<Chats> getChats(@RequestBody Chats chats) {
        QueryWrapper<Chats> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("myid", chats.getMyid());
        queryWrapper.eq("youid", chats.getYouid());
        return iChatsService.list(queryWrapper);
    }

    @PostMapping("addChat")
    public void addChat(@RequestBody Chats chats) {
        if (!chats.getMessage().equals("")
                || chats.getMyid().equals("") || chats.getYouid().equals("") || chats.getLabel().equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            chats.setDate(df.format(new Date()));// new Date()为获取当前系统时间，也可使用当前时间戳
            iChatsService.save(chats);
        }
    }
}
