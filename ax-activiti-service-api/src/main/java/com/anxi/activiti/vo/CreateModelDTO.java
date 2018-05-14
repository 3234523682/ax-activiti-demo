package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 新建模型Vo
 * Created by LJ on 2018/3/22
 */
@Data
public class CreateModelDTO implements Serializable {

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型KEY
     */
    private String modelKey;

    /**
     * 模型描述
     */
    private String modelDescription;

    /**
     * 模型分类
     */
    private String modelCategory;

    public CreateModelDTO() {
    }

    public CreateModelDTO(String modelName, String modelKey, String modelDescription, String modelCategory) {
        this.modelName = modelName;
        this.modelKey = modelKey;
        this.modelDescription = modelDescription;
        this.modelCategory = modelCategory;
    }
}
