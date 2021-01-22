package com.blb.wfx_mber;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.blb.wfx_mber.dao")
public class WfxMberApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxMberApplication.class, args);
    }

}
