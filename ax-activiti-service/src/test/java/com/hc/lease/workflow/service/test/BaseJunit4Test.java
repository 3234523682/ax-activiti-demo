package com.hc.lease.workflow.service.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by LJ on 2018/3/26
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:META-INF/spring/ApplicationContext.xml"}) //加载配置文件
public class BaseJunit4Test {
}
