package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/26
 */
@Data
public class SetProcessCategoryDTO implements Serializable {

    /**
     * act 流程ID
     */
    private String procDefId;

    /**
     * 分类
     */
    private String category;

    public SetProcessCategoryDTO() {
    }

    public SetProcessCategoryDTO(String procDefId, String category) {
        this.procDefId = procDefId;
        this.category = category;
    }
}
