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
* @since 2022-03-19
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uuid;

    private String username;

    private String password;


}
