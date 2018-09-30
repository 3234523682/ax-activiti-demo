
package com.anxi.activiti.web.workflow.act.rest.diagram.services;

import com.anxi.activiti.act.rest.diagram.services.ActProcessInstanceHighlightsResource;
import com.anxi.activiti.web.workflow.act.rest.servlet.ActRestSupport;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

@RestController
public class ProcessInstanceHighlightsResourceController extends ActRestSupport {

    @Reference(version = "${activiti.service.version}")
    private ActProcessInstanceHighlightsResource actProcessInstanceHighlightsResource;

    @ResponseBody
    @RequestMapping(value = "/act/service/process-instance/{processInstanceId}/highlights", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public JsonNode getHighlighted(@PathVariable String processInstanceId) {
        return super.readTree(actProcessInstanceHighlightsResource.getHighlighted(processInstanceId));
    }

}
