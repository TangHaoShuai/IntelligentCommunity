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

    private String uDescribe;

    private String phone;

    private String sex;

    private String image;

    private String house;

    public User(String username, String password, Integer age, String uDescribe, String phone, String sex, String image, String house) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.uDescribe = uDescribe;
        this.phone = phone;
        this.sex = sex;
        this.image = image;
        this.house = house;
    }

    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getuDescribe() {
        return uDescribe;
    }

    public void setuDescribe(String uDescribe) {
        this.uDescribe = uDescribe;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", uDescribe='" + uDescribe + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", image='" + image + '\'' +
                ", house='" + house + '\'' +
                '}';
    }
}
