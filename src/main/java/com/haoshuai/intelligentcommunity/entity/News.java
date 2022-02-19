package com.haoshuai.intelligentcommunity.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    @TableField("imgUrl")
    private String imgUrl;

    @TableField("newTime")
    private String newTime;

    private String url;


}
