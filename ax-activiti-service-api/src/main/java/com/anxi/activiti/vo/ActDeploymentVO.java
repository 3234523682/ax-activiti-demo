package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * act流程部署对象
 * Created by LJ on 2018/4/23
 */
@Data
public class ActDeploymentVO implements Serializable {

    /**
     * 流程部署ID
     */
    private String actDeploymentId;

    /**
     * 流程部署名称
     */
    private String actDeploymentName;

    /**
     * 流程部署时间
     */
    private Date actDeploymentTime;

    /**
     * 流程部署分类
     */
    private String actDeploymentCategory;

    private String actDeploymentTenantId;

    public ActDeploymentVO() {
    }

    public ActDeploymentVO(String actDeploymentId, String actDeploymentName, Date actDeploymentTime, String actDeploymentCategory, String actDeploymentTenantId) {
        this.actDeploymentId = actDeploymentId;
        this.actDeploymentName = actDeploymentName;
        this.actDeploymentTime = actDeploymentTime;
        this.actDeploymentCategory = actDeploymentCategory;
        this.actDeploymentTenantId = actDeploymentTenantId;
    }
}
