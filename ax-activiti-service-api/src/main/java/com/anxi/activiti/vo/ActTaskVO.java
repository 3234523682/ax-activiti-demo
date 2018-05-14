package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActTaskVO implements Serializable {

    /**
     * act 任务ID
     */
    private String taskId;

    /**
     * act 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * act 任务归属流程名称
     */
    private String taskProcessName;

    /**
     * 当前节点名称
     */
    private String currentNodeName;

    /**
     * 当前节点归属个人
     */
    private String nodeAffiliationPerson;

    /**
     * 当前节点归属分组
     */
    private String nodeAffiliationGroup;

    /**
     * act 任务发起人
     */
    private String taskInitiator;

    /**
     * act 任务创建日期
     */
    private Date createDate;

    /**
     * 任务归属流程定义ID
     */
    private String taskProcDefId;

    /**
     * 任务归属流程节点定义的KEY
     */
    private String taskDefinitionKey;

    /**
     * 任务归属流程定义版本
     */
    private Integer taskProcDefVersion;

    /**
     * 任务归属流程执行ID
     */
    private String taskExecutionId;

    /**
     * 任务归属流程实例ID
     */
    private String taskProcInsId;

    /**
     * 任务流程变量
     */
    private Map<String, Object> processVariables;

    public ActTaskVO() {
    }

    public ActTaskVO(String taskId, String taskDescription, String currentNodeName, String nodeAffiliationPerson, Date createDate, String taskProcDefId, String taskProcInsId, Map<String, Object> processVariables) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.currentNodeName = currentNodeName;
        this.nodeAffiliationPerson = nodeAffiliationPerson;
        this.createDate = createDate;
        this.taskProcDefId = taskProcDefId;
        this.taskProcInsId = taskProcInsId;
        this.processVariables = processVariables;
    }
}
