package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoshuai.intelligentcommunity.entity.CommunityServices;
import com.haoshuai.intelligentcommunity.entity.PageEntity;
import com.haoshuai.intelligentcommunity.entity.Schedule;
import com.haoshuai.intelligentcommunity.entity.User;
import com.haoshuai.intelligentcommunity.entity.model.CommunityServicesModel;
import com.haoshuai.intelligentcommunity.service.ICommunityServicesService;
import com.haoshuai.intelligentcommunity.service.IScheduleService;
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

    @Autowired
    private IUserService iUserService;

    @PostMapping("getCommunityServices")
    public List<CommunityServices> getCommunityServices(@RequestBody CommunityServices communityServices) {
        QueryWrapper<CommunityServices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(communityServices.getState()), "state", communityServices.getState());
        return iCommunityServicesService.list(queryWrapper);
    }

    @PostMapping("deleteCommunityServices")
    public boolean deleteCommunityServices(@RequestBody CommunityServices communityServices) {
        if (communityServices.getUuid() != "" && communityServices.getUuid() != null) {
            QueryWrapper<CommunityServices> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", communityServices.getUuid());
            QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
            scheduleQueryWrapper.eq("serviceid", communityServices.getUuid());
            boolean temp = iCommunityServicesService.remove(queryWrapper);
            boolean temp_t = iScheduleService.remove(scheduleQueryWrapper);
            if (temp && temp_t) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @PostMapping("updateCommunityServices")
    public boolean updateCommunityServices(@RequestBody CommunityServices communityServices) {
        if (communityServices.getUuid() != "" && communityServices.getUuid() != null) {
            UpdateWrapper<CommunityServices> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", communityServices.getUuid());
            return iCommunityServicesService.update(communityServices, updateWrapper);
        } else {
            return false;
        }
    }

    @PostMapping("selectPage")
    public PageEntity selectPage(@RequestBody Map<String, String> map) {
        long current = Long.parseLong(map.get("current"));
        long size = Long.parseLong(map.get("size"));
        String message = map.get("message");
        String state = map.get("state");
        QueryWrapper<CommunityServices> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(message), "message", message);
        queryWrapper.like(StringUtils.isNotEmpty(state), "state", state);
        Page<CommunityServices> page = new Page<>(current, size);
        iCommunityServicesService.page(page, queryWrapper);
        PageEntity pageEntity = new PageEntity(page);
        List<CommunityServicesModel> communityServicesModels = new ArrayList<>();
        for (Object s : pageEntity.getList()) {
            CommunityServices cs = (CommunityServices) s;
            CommunityServicesModel servicesModel = new CommunityServicesModel();
            servicesModel.setUserid(cs.getUserid());
            servicesModel.setDate(cs.getDate());
            servicesModel.setUuid(cs.getUuid());
            servicesModel.setState(cs.getState());
            servicesModel.setMessage(cs.getMessage());
//            Schedule
            QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
            scheduleQueryWrapper.eq("serviceid", cs.getUuid());
            List<Schedule> scheduleList = iScheduleService.list(scheduleQueryWrapper);
            //排序
            scheduleList.sort(Comparator.comparing(Schedule::getDate));
//            Collections.reverse(scheduleList);

            servicesModel.setScheduleList(scheduleList);
//            user
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("phone", cs.getUserid());
            User user = iUserService.getOne(userQueryWrapper);
            servicesModel.setUser(user);

            communityServicesModels.add(servicesModel);
        }
        pageEntity.setList(communityServicesModels);

        return pageEntity;
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
//        保存成功继续保存进度
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























