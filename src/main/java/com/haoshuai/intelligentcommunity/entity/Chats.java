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
 * @since 2022-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Chats implements Serializable {

    private static final long serialVersionUID = 1L;

    private String myid;

    private String message;

    private String youid;

    private String date;

    private String label;


}
