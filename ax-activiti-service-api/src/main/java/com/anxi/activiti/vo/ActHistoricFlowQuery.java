package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * act 任务历史节点查询
 * Created by LJ on 2018/4/9
 */
@Data
public class ActHistoricFlowQuery implements Serializable {

    /**
     * 流程实例ID
     */
    private String procInsId;

    /**
     * 开始节点实例ID
     */
    private String startActId;

    /**
     * 结束节点实例ID
     */
    private String endActId;

    public ActHistoricFlowQuery() {
    }

    public ActHistoricFlowQuery(String procInsId) {
        this.procInsId = procInsId;
    }
}
