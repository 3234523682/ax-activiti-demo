package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActTaskClaimDTO implements Serializable {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 用户ID
     */
    private String userId;

    public ActTaskClaimDTO() {
    }

    public ActTaskClaimDTO(String taskId, String userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}
