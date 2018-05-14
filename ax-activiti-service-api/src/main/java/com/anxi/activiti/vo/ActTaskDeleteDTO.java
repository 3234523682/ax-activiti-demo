package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
public class ActTaskDeleteDTO implements Serializable {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 删除原因
     */
    private String deleteReason;
}
