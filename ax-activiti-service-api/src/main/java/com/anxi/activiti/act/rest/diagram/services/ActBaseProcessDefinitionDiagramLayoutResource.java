package com.anxi.activiti.act.rest.diagram.services;

/**
 * 基本流程定义图布局资源
 * Created by LJ on 2018/3/27
 */
public interface ActBaseProcessDefinitionDiagramLayoutResource {

    String getDiagramNode(String processInstanceId, String processDefinitionId);

}
