package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Act 流程定义对象
 * Created by LJ on 2018/4/2
 */
@Data
public class ActProcessDefinitionVO implements Serializable {

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 流程定义标识
     */
    private String procDefKey;

    /**
     * 流程定义分类
     */
    private String category;

    /**
     * 流程定义名称
     */
    private String procDefName;

    /**
     * 流程定义版本
     */
    private Integer procDefVersion;

    /**
     * 流程部署ID
     */
    private String deploymentId;

    /**
     * 流程定义部署时间
     */
    private Date procDefDeployTime;

    /**
     * 流程挂起
     */
    private Boolean suspended;

    /**
     * 流程定义资源XML
     */
    private String procDefResourceXml;

    /**
     * 流程定义资源图片
     */
    private String procDefResourceImg;
}
