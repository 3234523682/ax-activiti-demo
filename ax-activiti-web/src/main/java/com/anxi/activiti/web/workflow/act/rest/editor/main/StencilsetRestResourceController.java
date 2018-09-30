package com.anxi.activiti.web.workflow.act.rest.editor.main;

import com.anxi.activiti.act.rest.editor.main.ActStencilsetRestResource;
import com.anxi.activiti.web.workflow.act.rest.servlet.ActRestSupport;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author Tijs Rademakers
 */
@RestController
public class StencilsetRestResourceController extends ActRestSupport {

    @Reference(version = "${activiti.service.version}")
    private ActStencilsetRestResource actStencilsetRestResource;

    @ResponseBody
    @RequestMapping(value = "/act/service/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public JsonNode getStencilset() {
        return super.readTree(actStencilsetRestResource.getStencilset());
    }
}
