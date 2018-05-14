package com.anxi.activiti.constant;

import lombok.Getter;

import java.io.Serializable;

/**
 * act 常用任务节点类型
 * Created by LJ on 2018/4/9
 */
@Getter
public enum ActCommonActivityType implements Serializable {

    /**
     * 开始事件
     */
    startEvent("开始事件", "startEvent"),

    /**
     * 结束事件
     */
    endEvent("结束事件", "endEvent");

    private String name;

    private String value;

    ActCommonActivityType(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
