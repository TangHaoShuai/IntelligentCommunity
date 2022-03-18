package com.haoshuai.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haoshuai.intelligentcommunity.entity.Schedule;
import com.haoshuai.intelligentcommunity.entity.model.CommentModel;
import com.haoshuai.intelligentcommunity.service.IScheduleService;
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
 * 服务进度
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IScheduleService iScheduleService;

    @PostMapping("addSchedule")
    public boolean addSchedule(@RequestBody Schedule schedule) {
        if (schedule.getServiceid() != null && schedule.getServiceid() != "") {
            String schedule_uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            SimpleDateFormat schedule_df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            schedule.setUuid(schedule_uuid);
            schedule.setServiceid(schedule.getServiceid());
            schedule.setDate(schedule_df.format(new Date()));
            return iScheduleService.save(schedule);
        } else {
            return false;
        }
    }

    @PostMapping("updateSchedule")
    public boolean updateSchedule(@RequestBody Schedule schedule) {
        if (schedule.getUuid() != null && schedule.getUuid() != "") {
            QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", schedule.getUuid());
            return iScheduleService.update(schedule, queryWrapper);
        }
        return false;
    }

    @PostMapping("deleteSchedule")
    public boolean deleteSchedule(@RequestBody Schedule schedule){
        if (schedule.getUuid() != null && schedule.getUuid() != "") {
            QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", schedule.getUuid());
            return iScheduleService.remove(queryWrapper);
        }
        return false;
    }


    @PostMapping("getSchedule")
    public List<Schedule> getSchedule(@RequestBody Schedule schedule) {
        if (schedule.getServiceid() == null || schedule.getServiceid() == "") {
            return null;
        }
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serviceid", schedule.getServiceid());
        List<Schedule> schedules = iScheduleService.list(queryWrapper);
        schedules.sort(Comparator.comparing(Schedule::getDate));
//        Collections.reverse(schedules);
        return schedules;
    }
}
