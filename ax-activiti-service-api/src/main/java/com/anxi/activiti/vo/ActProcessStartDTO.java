package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActProcessStartDTO implements Serializable {

    /**
     * 启动流程用户ID
     */
    private String processStartUserId;

    /**
     * 流程ID
     */
    private String procDefId;

    /**
     * 流程标识
     */
    private String procDefKey;

    /**
     * 流程标题
     */
    private String processTitle;

    public ActProcessStartDTO() {
    }

    public ActProcessStartDTO(String processStartUserId, String procDefKey, String processTitle) {
        this.processStartUserId = processStartUserId;
        this.procDefKey = procDefKey;
        this.processTitle = processTitle;
    }
}
