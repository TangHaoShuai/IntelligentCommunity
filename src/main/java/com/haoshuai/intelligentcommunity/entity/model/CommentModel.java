package com.haoshuai.intelligentcommunity.entity.model;

import com.haoshuai.intelligentcommunity.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentModel {


    private String articleid;

    private String userid;

    private User user;

    private String date;

    private String message;

    private String uuid;

}
