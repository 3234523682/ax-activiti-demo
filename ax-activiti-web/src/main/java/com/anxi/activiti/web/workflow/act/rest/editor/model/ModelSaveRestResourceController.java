package com.anxi.activiti.web.workflow.act.rest.editor.model;

import com.anxi.activiti.act.rest.editor.model.ActModelSaveRestResource;
import com.anxi.activiti.vo.ActModelSaveDTO;
import com.anxi.activiti.web.conf.UrlParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

@RestController
public class ModelSaveRestResourceController {

    @Reference(version = "${activiti.service.version}")
    private ActModelSaveRestResource actModelSaveRestResource;

    @RequestMapping(value = "/act/service/model/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @UrlParam ActModelSaveDTO actModelSaveDTO) {
        actModelSaveDTO.setModelId(modelId);
        actModelSaveRestResource.saveModel(actModelSaveDTO);
    }
}
