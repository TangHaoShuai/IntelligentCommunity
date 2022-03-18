package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.Schedule;
import com.haoshuai.intelligentcommunity.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityServicesModel {
    public static final String state_0 = "等待处理";
    public static final String state_1 = "正在处理";
    public static final String state_2 = "处理完成";

    private String uuid;

    private String userid;

    private String message;

    private String date;

    private String state;

    private List<Schedule>scheduleList;

    private User user;
}
