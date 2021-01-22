package com.blb.wfx_cust;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.blb.wfx_cust.dao")
public class WfxCustApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxCustApplication.class, args);
    }

}
