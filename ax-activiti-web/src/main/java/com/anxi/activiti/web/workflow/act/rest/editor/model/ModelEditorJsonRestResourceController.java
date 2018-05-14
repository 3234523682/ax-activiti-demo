package com.anxi.activiti.web.workflow.act.rest.editor.model;

import com.anxi.activiti.act.rest.editor.model.ActModelEditorJsonRestResource;
import com.anxi.activiti.web.workflow.act.rest.servlet.ActRestSupport;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
public class ModelEditorJsonRestResourceController extends ActRestSupport {

    @Resource
    private ActModelEditorJsonRestResource actModelEditorJsonRestResource;

    @ResponseBody
    @RequestMapping(value = "/act/service/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public JsonNode getEditorJson(@PathVariable String modelId) {
        return super.readTree(actModelEditorJsonRestResource.getEditorJson(modelId));
    }

}
