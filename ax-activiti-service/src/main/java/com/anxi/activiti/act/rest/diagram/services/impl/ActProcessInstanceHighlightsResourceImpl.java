package com.anxi.activiti.act.rest.diagram.services.impl;

import com.anxi.activiti.act.rest.diagram.services.ActProcessInstanceHighlightsResource;
import org.activiti.rest.diagram.services.ProcessInstanceHighlightsResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by LJ on 2018/3/27
 */
@Component("actProcessInstanceHighlightsResource")
public class ActProcessInstanceHighlightsResourceImpl implements ActProcessInstanceHighlightsResource {

    @Resource
    private ProcessInstanceHighlightsResource processInstanceHighlightsResource;

    @Override
    public String getHighlighted(String processInstanceId) {
        return processInstanceHighlightsResource.getHighlighted(processInstanceId).toString();
    }


}
