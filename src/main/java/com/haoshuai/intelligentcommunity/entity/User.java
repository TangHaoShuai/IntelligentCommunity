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
* @since 2022-02-20
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private Integer age;

    private String u_describe;

    private String phone;

    private String sex;


}
