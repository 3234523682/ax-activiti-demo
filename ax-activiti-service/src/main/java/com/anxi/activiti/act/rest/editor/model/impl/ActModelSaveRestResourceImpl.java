package com.anxi.activiti.act.rest.editor.model.impl;

import com.anxi.activiti.act.rest.editor.model.ActModelSaveRestResource;
import com.anxi.activiti.vo.ActModelSaveDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
@Component("actModelSaveRestResource")
public class ActModelSaveRestResourceImpl implements ActModelSaveRestResource {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private RepositoryService repositoryService;


    @Override
    @Transactional
    public void saveModel(ActModelSaveDTO actModelSaveDTO) {
        //modelSaveRestResource.saveModel(modelId, values);
        try {
            Model model = this.repositoryService.getModel(actModelSaveDTO.getModelId());
            ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
            modelJson.put("name", actModelSaveDTO.getName());
            modelJson.put("description", actModelSaveDTO.getDescription());
            model.setMetaInfo(modelJson.toString());
            model.setName(actModelSaveDTO.getName());
            this.repositoryService.saveModel(model);
            this.repositoryService.addModelEditorSource(model.getId(), (actModelSaveDTO.getJson_xml()).getBytes("utf-8"));
            InputStream svgStream = new ByteArrayInputStream((actModelSaveDTO.getSvg_xml()).getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            transcoder.transcode(input, output);
            byte[] result = outStream.toByteArray();
            this.repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception var11) {
            log.error("Error saving model", var11);
            throw new ActivitiException("Error saving model", var11);
        }
    }
}
