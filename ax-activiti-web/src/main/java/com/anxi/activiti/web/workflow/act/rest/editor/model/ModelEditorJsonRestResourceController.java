package com.anxi.activiti.web.workflow.act.rest.editor.model;

import com.anxi.activiti.act.rest.editor.model.ActModelEditorJsonRestResource;
import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.web.workflow.act.rest.servlet.ActRestSupport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;


@Slf4j
@RestController
public class ModelEditorJsonRestResourceController extends ActRestSupport {

    @Resource
    private ActModelEditorJsonRestResource actModelEditorJsonRestResource;

    @Resource
    private ActProcessService actProcessService;


    @ResponseBody
    @RequestMapping(value = "/act/service/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public JsonNode getEditorJson(@PathVariable String modelId) {
        return super.readTree(actModelEditorJsonRestResource.getEditorJson(modelId));
    }

    @ResponseBody
    @RequestMapping(value = "/act/service/procDef/{procDefId}/json", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ObjectNode getEditorJsonByProcDefId(@PathVariable String procDefId) throws IOException, XMLStreamException {
        ActProcessDefinitionVO actProcessDefinitionVO = actProcessService.getProcessDefinitionById(procDefId);
        ObjectNode objectNode = super.objectMapper.createObjectNode();
        objectNode.put("modelId", actProcessDefinitionVO.getProcDefId());
        objectNode.put("name", actProcessDefinitionVO.getProcDefName());
        objectNode.put("revision", actProcessDefinitionVO.getProcDefVersion());
        objectNode.put("description", actProcessDefinitionVO.getProcDefDescription());
        String json = actModelEditorJsonRestResource.getEditorJsonByProcDefId(procDefId);
        JsonNode jsonNode = this.objectMapper.readTree(json);
        objectNode.put("model", jsonNode);
        return objectNode;
    }
}
