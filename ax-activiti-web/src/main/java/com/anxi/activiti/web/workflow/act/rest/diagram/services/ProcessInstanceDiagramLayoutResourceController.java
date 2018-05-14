package com.anxi.activiti.web.workflow.act.rest.diagram.services;

import com.anxi.activiti.act.rest.diagram.services.ActBaseProcessDefinitionDiagramLayoutResource;
import com.anxi.activiti.web.workflow.act.rest.servlet.ActRestSupport;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProcessInstanceDiagramLayoutResourceController extends ActRestSupport {

    @Resource
    private ActBaseProcessDefinitionDiagramLayoutResource actBaseProcessDefinitionDiagramLayoutResource;

    @ResponseBody
    @RequestMapping(value = "/act/service/process-instance/{processInstanceId}/diagram-layout", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public JsonNode getDiagram(@PathVariable String processInstanceId) {
        return super.readTree(actBaseProcessDefinitionDiagramLayoutResource.getDiagramNode(processInstanceId, null));
    }

}
