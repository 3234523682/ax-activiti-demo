package com.anxi.activiti.web.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LJ on 2018/4/23
 */
@RestController
public class TestController {

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "helloWorld";
    }

}
