package com.haoshuai.intelligentcommunity.entity;

    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 进度
    * </p>
*
* @author TangHaoShuai
* @since 2022-03-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String title;

    private String message;

    private String date;

    private String serviceid;


}
