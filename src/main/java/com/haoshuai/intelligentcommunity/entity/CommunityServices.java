package com.haoshuai.intelligentcommunity.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommunityServices implements Serializable {
    public static final String state_0 = "等待处理";
    public static final String state_1 = "正在处理";
    public static final String state_2 = "处理完成";

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String userid;

    private String message;

    private String date;

    private String state;


}
