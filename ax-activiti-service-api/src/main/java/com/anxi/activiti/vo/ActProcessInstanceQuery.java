package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActProcessInstanceQuery extends CommonQuery implements Serializable {

    /**
     * act 流程实例Id
     */
    private String procInsId;

    /**
     * act 流程实例名称
     */
    private String procInsName;

    /**
     * act 流程定义Key
     */
    private String procDefKey;

    public ActProcessInstanceQuery() {
    }

    public ActProcessInstanceQuery(String procInsId, String procDefKey) {
        this.procInsId = procInsId;
        this.procDefKey = procDefKey;
    }

    public ActProcessInstanceQuery(int pageNum, int pageSize, String procInsId, String procDefKey) {
        super(pageNum, pageSize);
        this.procInsId = procInsId;
        this.procDefKey = procDefKey;
    }
}
