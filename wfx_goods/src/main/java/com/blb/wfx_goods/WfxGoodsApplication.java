package com.blb.wfx_goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.blb.wfx_goods.dao")
public class WfxGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxGoodsApplication.class, args);
    }

}
