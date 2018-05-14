package com.anxi.activiti.act.rest.editor.main.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.anxi.activiti.act.rest.editor.main.ActStencilsetRestResource;
import org.activiti.engine.ActivitiException;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by LJ on 2018/3/27
 */
@Component("actStencilsetRestResource")
public class ActStencilsetRestResourceImpl implements ActStencilsetRestResource {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getStencilset() {
        try {
            InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
            //return IOUtils.toString(stencilsetStream, "utf-8");
            return objectMapper.readTree(stencilsetStream).toString();
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
}
