package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * act 任务历史节点信息
 * Created by LJ on 2018/4/9
 */
@Data
public class ActHistoricActivityVO implements Serializable {

    /**
     * 主键ID
     */
    public String id;

    /**
     * 归属任务ID
     */
    public String activityId;

    /**
     * 归属任务名称
     */
    public String activityName;

    /**
     * 任务节点类型
     */
    public String activityType;

    /**
     * 流程定义ID
     */
    public String processDefinitionId;

    /**
     * 流程实例ID
     */
    public String processInstanceId;

    /**
     * 流程执行ID
     */
    public String executionId;

    /**
     * 任务ID
     */
    public String taskId;

    /**
     * 执行人标识
     */
    public String assignee;

    /**
     * 执行人姓名
     */
    public String assigneeName;

    /**
     * 任务节点审核类型
     */
    private String auditType;

    /**
     * 审批意见
     */
    private String comment;

    /**
     * 开始时间
     */
    public Date startTime;

    /**
     * 结束时间
     */
    public Date endTime;

    /**
     * 任务历时（毫秒数）
     */
    public Long durationInMillis;

}
