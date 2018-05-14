package com.anxi.activiti.web.workflow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 流程模型控制器
 *
 * @author henryyan
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public String index() {
        return "/index";
    }

}
