package com.anxi.activiti.act.rest.editor.model.impl;

import com.anxi.activiti.act.rest.editor.model.ActModelEditorJsonRestResource;
import lombok.extern.slf4j.Slf4j;
import org.activiti.rest.editor.model.ModelEditorJsonRestResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("actModelEditorJsonRestResource")
public class ActModelEditorJsonRestResourceImpl implements ActModelEditorJsonRestResource {

    @Resource
    private ModelEditorJsonRestResource modelEditorJsonRestResource;

    @Override
    public String getEditorJson(String modelId) {
        return modelEditorJsonRestResource.getEditorJson(modelId).toString();
    }
}
