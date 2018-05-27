package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActProcessStartDTO implements Serializable {

    /**
     * 启动流程用户ID（必须）
     */
    private String processStartUserId;

    /**
     * 流程ID
     */
    private String procDefId;

    /**
     * 流程标识（必须）
     */
    private String procDefKey;

    /**
     * 流程标题（必须）
     */
    private String processTitle;

    /**
     * 流程参数json字符串
     */
    private String flowParamJsonStr;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程名称
     */
    private String procDefName;

    public ActProcessStartDTO() {
    }

    public ActProcessStartDTO(String processStartUserId, String procDefKey, String processTitle) {
        this.processStartUserId = processStartUserId;
        this.procDefKey = procDefKey;
        this.processTitle = processTitle;
    }
}
