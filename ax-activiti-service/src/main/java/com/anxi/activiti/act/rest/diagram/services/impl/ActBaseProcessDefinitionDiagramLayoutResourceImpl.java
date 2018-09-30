package com.anxi.activiti.act.rest.diagram.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anxi.activiti.act.rest.diagram.services.ActBaseProcessDefinitionDiagramLayoutResource;
import com.anxi.activiti.act.rest.diagram.services.BaseProcessDefinitionDiagramLayoutResource;

import javax.annotation.Resource;

/**
 * 基本流程定义图布局资源
 * Created by LJ on 2018/3/27
 */
@Service(path = "actBaseProcessDefinitionDiagramLayoutResource", version = "${activiti.service.version}")
public class ActBaseProcessDefinitionDiagramLayoutResourceImpl implements ActBaseProcessDefinitionDiagramLayoutResource {

    @Resource
    private BaseProcessDefinitionDiagramLayoutResource baseProcessDefinitionDiagramLayoutResource;

    @Override
    public String getDiagramNode(String processInstanceId, String processDefinitionId) {
        return baseProcessDefinitionDiagramLayoutResource.getDiagramNode(processInstanceId, processDefinitionId).toString();
    }

}
