package com.anxi.activiti.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 该注解指定项目为springboot，由此类当作程序入口
 * 自动装配 web 依赖的环境
 * ps：spring boot 默认扫描您的类是 在启动类的“当前包”和“下级包”
 **/
@SpringBootApplication
public class ActivitiWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ActivitiWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiWebApplication.class, args);
    }

/*
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootApp.class).web(WebApplicationType.SERVLET).run(args);
    }*/
}
