package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActProcessDefinitionQuery extends CommonQuery implements Serializable {

    /**
     * 流程标识
     */
    private String procDefKey;

    /**
     * 流程名称
     */
    private String procDefName;

    /**
     * 流程定义版本
     */
    private Integer procDefVersion;

    /**
     * act 流程分类
     */
    private String category;

    public ActProcessDefinitionQuery() {
    }

    public ActProcessDefinitionQuery(String category, int pageNum, int pageSize) {
        super(pageNum, pageSize);
        this.category = category;
    }
}
