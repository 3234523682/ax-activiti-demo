package com.anxi.activiti.act.rest.diagram.services.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anxi.activiti.act.rest.diagram.services.ActProcessInstanceHighlightsResource;
import com.anxi.activiti.act.rest.diagram.services.ProcessInstanceHighlightsResource;

import javax.annotation.Resource;

/**
 * Created by LJ on 2018/3/27
 */
@Service(path = "actProcessInstanceHighlightsResource", version = "${activiti.service.version}")
public class ActProcessInstanceHighlightsResourceImpl implements ActProcessInstanceHighlightsResource {

    @Resource
    private ProcessInstanceHighlightsResource processInstanceHighlightsResource;

    @Override
    public String getHighlighted(String processInstanceId) {
        return processInstanceHighlightsResource.getHighlighted(processInstanceId).toString();
    }


}
