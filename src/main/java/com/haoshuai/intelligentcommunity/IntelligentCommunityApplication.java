package com.haoshuai.intelligentcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.haoshuai.intelligentcommunity.mapper") //扫描mapper存放的地址
public class IntelligentCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentCommunityApplication.class, args);
    }

}
