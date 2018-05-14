package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * act模型对象
 * Created by LJ on 2018/4/23
 */
@Data
public class ActModelVO implements Serializable {

    /**
     * act模型ID
     */
    private String actModelId;

    /**
     * act模型名称
     */
    private String actModelName;

    /**
     * act模型key
     */
    private String actModelKey;

    /**
     * act模型分类
     */
    private String actModelCategory;

    /**
     * act模型创建日期
     */
    private Date actModelCreateTime;

    /**
     * act模型最后更新日期
     */
    private Date actModelLastUpdateTime;

    /**
     * act模型当期版本
     */
    private Integer actModelVersion;

    /**
     * act模型 以json格式保存流程定义的信息
     */
    private String actModelMetaInfo;

    /**
     * act模型所属流程部署ID
     */
    private String actModelDeploymentId;

    private String actModelTenantId;

    /**
     * whether this model has editor source
     */
    private boolean hasEditorSource;

    /**
     * whether this model has editor source extra
     */
    private boolean hasEditorSourceExtra;
}
