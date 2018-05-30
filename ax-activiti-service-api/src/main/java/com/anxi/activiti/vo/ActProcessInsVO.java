package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Act 流程实例
 * Created by LJ on 2018/4/2
 */
@Data
public class ActProcessInsVO implements Serializable {

    /**
     * 流程执行ID
     */
    private String id;

    /**
     * 流程实例ID
     */
    private String procInsId;

    /**
     * 流程实例名称
     */
    private String procInsName;

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 流程定义标识
     */
    private String procDefKey;

    /**
     * 流程定义名称
     */
    private String procDefName;

    /**
     * 流程定义版本
     */
    private Integer procDefVersion;

    /**
     * 当前环节标识
     */
    private String activityId;

    /**
     * 当前环节名称
     */
    private String activityName;

    /**
     * 当前环节受理人
     */
    private String activityConductor;

    /**
     * 是否挂起
     */
    private boolean suspended;

    public ActProcessInsVO() {
    }

    public ActProcessInsVO(String id, String processInsId, String processInsName, String procDefId, String activityId, boolean suspended) {
        this.id = id;
        this.procInsId = processInsId;
        this.procInsName = processInsName;
        this.procDefId = procDefId;
        this.activityId = activityId;
        this.suspended = suspended;
    }
}
