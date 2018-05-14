package com.anxi.activiti.web.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by LJ on 2018/4/23
 */
@Configuration
@ImportResource({ "classpath:dubbo/*.xml" })
public class DubboConfig {
}
