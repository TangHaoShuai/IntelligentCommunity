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
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImgList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String imgid;

    private String imgUrl;

    private String date;

    private String imgDescribe;


}
