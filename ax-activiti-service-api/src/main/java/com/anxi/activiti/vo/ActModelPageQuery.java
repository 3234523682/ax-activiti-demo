package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActModelPageQuery extends CommonQuery implements Serializable {


    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 模型KEY
     */
    private String modelKey;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型分类
     */
    private String modelCategory;

    public ActModelPageQuery() {
    }

}
