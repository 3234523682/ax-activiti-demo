package com.anxi.activiti.act.rest.editor.model.impl;

import com.anxi.activiti.act.rest.editor.model.ActModelEditorJsonRestResource;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.editor.model.ModelEditorJsonRestResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component("actModelEditorJsonRestResource")
public class ActModelEditorJsonRestResourceImpl implements ActModelEditorJsonRestResource {

    @Resource
    private ModelEditorJsonRestResource modelEditorJsonRestResource;

    @Resource
    private RepositoryService repositoryService;

    @Override
    public String getEditorJson(String modelId) {
        return modelEditorJsonRestResource.getEditorJson(modelId).toString();
    }

    @Override
    public String getEditorJsonByProcDefId(String procDefId) throws XMLStreamException, UnsupportedEncodingException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        return modelNode.toString();
    }
}
