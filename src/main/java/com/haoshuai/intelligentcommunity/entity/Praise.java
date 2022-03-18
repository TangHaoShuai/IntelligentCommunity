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
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Praise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String articleid; //文章ID

    private String userid;  //点赞者ID

    private String date;  //时间

    private String uuid;  //uuid


}
