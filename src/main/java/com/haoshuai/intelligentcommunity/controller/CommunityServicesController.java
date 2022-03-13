package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.haoshuai.intelligentcommunity.entity.CommunityServices;
import com.haoshuai.intelligentcommunity.entity.Schedule;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.service.ICommunityServicesService;
import com.haoshuai.intelligentcommunity.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * 社区服务
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/community-services")
public class CommunityServicesController {
    @Autowired
    private ICommunityServicesService iCommunityServicesService;

    @Autowired
    private IScheduleService iScheduleService;

    @PostMapping("getCommunityServices")
    public List<CommunityServices> getCommunityServices(@RequestBody CommunityServices communityServices) {
        QueryWrapper<CommunityServices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(communityServices.getState()), "state", communityServices.getState());
        return iCommunityServicesService.list(queryWrapper);
    }

    @PostMapping("addCommunityServices")
    public boolean addCommunityServices(@RequestBody CommunityServices communityServices) {
        if (communityServices.getUserid() == "" || communityServices.getUserid() == null) {
            return false;
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        communityServices.setUuid(uuid);
        communityServices.setDate(df.format(new Date()));
        communityServices.setState(CommunityServices.state_0);
        if (iCommunityServicesService.save(communityServices)) {
            Schedule schedule = new Schedule();
            String schedule_uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            SimpleDateFormat schedule_df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            schedule.setUuid(schedule_uuid);
            schedule.setServiceid(uuid);
            schedule.setDate(schedule_df.format(new Date()));
            schedule.setTitle("等待管理员处理");
            schedule.setMessage("消息已经反馈到管理员，请耐心等待！！管理员电话:18077229249");
            return iScheduleService.save(schedule);
        } else {
            return false;
        }
    }

}























