package com.blb.wfx_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WfxLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxLoginApplication.class, args);
    }

}
