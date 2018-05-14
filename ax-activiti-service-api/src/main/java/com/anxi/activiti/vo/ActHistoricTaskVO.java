package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LJ on 2018/4/8
 */
@Data
public class ActHistoricTaskVO extends ActTaskVO implements Serializable {

    /**
     * 任务开始时间
     */
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    private Date taskEndTime;

    /**
     * 任务签收时间
     */
    private Date taskClaimTime;

    /**
     * 任务持续毫秒数（任务从开始到结束所耗毫秒数）
     */
    private Long taskDurationMillis;

    /**
     * 任务工作毫秒数（任务从签收到结束所耗毫秒数）
     */
    private Long taskWorkTimeMillis;

    /**
     * 任务完结
     */
    private Boolean taskEnd;

    public ActHistoricTaskVO() {
    }

    public ActHistoricTaskVO(Date taskStartTime, Date taskEndTime, Date taskClaimTime, Long taskDurationMillis, Long taskWorkTimeMillis, Boolean taskEnd) {
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.taskClaimTime = taskClaimTime;
        this.taskDurationMillis = taskDurationMillis;
        this.taskWorkTimeMillis = taskWorkTimeMillis;
        this.taskEnd = taskEnd;
    }

    public void setActTaskInfo(ActTaskVO actTask) {
        super.setTaskId(actTask.getTaskId());
        super.setTaskName(actTask.getTaskName());
        super.setTaskProcessName(actTask.getTaskProcessName());
        super.setTaskDescription(actTask.getTaskDescription());
        super.setCurrentNodeName(actTask.getCurrentNodeName());
        super.setNodeAffiliationPerson(actTask.getNodeAffiliationPerson());
        super.setCreateDate(actTask.getCreateDate());
        super.setTaskInitiator(actTask.getTaskInitiator());
        super.setTaskProcDefId(actTask.getTaskProcDefId());
        super.setTaskProcInsId(actTask.getTaskProcDefId());
        super.setProcessVariables(actTask.getProcessVariables());
    }

}
