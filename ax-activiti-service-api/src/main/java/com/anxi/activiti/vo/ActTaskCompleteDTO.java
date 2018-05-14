package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActTaskCompleteDTO implements Serializable {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 流程实例ID
     */
    private String procInsId;

    /**
     * 意见类型（com.hc.lease.workflow.constant.ActCommentType）
     */
    private String commentType;

    /**
     * 任务提交意见的内容
     */
    private String comment;

    /**
     * 任务变量
     */
    private Map<String, Object> vars;

    public ActTaskCompleteDTO(String taskId, String procInsId, String commentType, String comment, Map<String, Object> vars) {
        this.taskId = taskId;
        this.procInsId = procInsId;
        this.commentType = commentType;
        this.comment = comment;
        this.vars = vars;
    }
}
