package com.anxi.activiti;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author linjing
 */
@SpringBootApplication
@MapperScan("com.anxi.dubbo.service.dao")
public class ActivitiServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ActivitiServiceApplication.class).web(WebApplicationType.NONE).run(args);
    }
}