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
* @since 2022-03-14
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Share implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String name;

    private String description;

    private String userid;

    private String begdate;

    private String enddate;

    private String state;

    private String img;

    private String qrimg;

    @Override
    public String toString() {
        return "{" +
                "uuid:'" + uuid + '\'' +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", userid:'" + userid + '\'' +
                ", begdate:'" + begdate + '\'' +
                ", enddate:'" + enddate + '\'' +
                ", state:'" + state + '\'' +
                ", img:'" + img + '\'' +
                ", qrimg:'" + qrimg + '\'' +
                '}';
    }
}
