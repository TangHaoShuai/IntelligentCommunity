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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * 服务进度
 * </p>
 * @author TangHaoShuai
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IScheduleService iScheduleService;

    @PostMapping("getSchedule")
    public List<Schedule> getSchedule(@RequestBody Schedule schedule) {
        if (schedule.getServiceid() == null || schedule.getServiceid() == "") {
            return null;
        }
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serviceid", schedule.getServiceid());
        List<Schedule> schedules = iScheduleService.list(queryWrapper);
        schedules.sort(Comparator.comparing(Schedule::getDate));
        Collections.reverse(schedules);
        return  schedules;
    }
}
