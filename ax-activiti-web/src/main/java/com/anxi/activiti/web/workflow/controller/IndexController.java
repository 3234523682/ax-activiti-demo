package com.anxi.activiti.web.workflow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping(value = "/workflow")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index() {
        return "/index";
    }

}
