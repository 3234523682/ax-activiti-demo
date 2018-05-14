package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LJ on 2018/3/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActTaskPageQuery extends CommonQuery implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 任务归属流程实例ID（流水号）
     */
    private String taskProcInsId;

    /**
     * 任务名称
     */
    private String taskProcessInstanceName;

    /**
     * 任务节点名称（经办步骤名称）
     */
    private String taskNodeName;

    /**
     * 任务发起人
     */
    private String taskInitiator;

    /**
     * 流程定义Key（流程定义标识）
     */
    private String procDefKey;

    /**
     * 流程定义名称
     */
    private String procDefName;

    /**
     * 开始查询日期
     */
    private Date beginDate;

    /**
     * 结束查询日期
     */
    private Date endDate;

    public ActTaskPageQuery(int pageNum, int pageSize, String userId, String procDefKey) {
        super(pageNum, pageSize);
        this.userId = userId;
        this.procDefKey = procDefKey;
    }

    public ActTaskPageQuery() {
    }

}
