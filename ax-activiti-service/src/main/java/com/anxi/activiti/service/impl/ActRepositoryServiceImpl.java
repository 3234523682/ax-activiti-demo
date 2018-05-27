package com.anxi.activiti.service.impl;

import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.vo.ActModelNodeInfoVo;
import com.anxi.activiti.vo.ActModelResourceDTO;
import com.anxi.activiti.vo.SetProcessCategoryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.anxi.activiti.service.api.ActRepositoryService;
import com.anxi.activiti.vo.ActDeploymentVO;
import com.anxi.activiti.vo.ActModelPageQuery;
import com.anxi.activiti.vo.ActModelVO;
import com.anxi.activiti.vo.CreateModelDTO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by LJ on 2018/3/22
 */
@Slf4j
@Service("actRepositoryService")
public class ActRepositoryServiceImpl implements ActRepositoryService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ActProcessService actProcessService;

    @Override
    public List<ActModelVO> findAllModels() {
        return this.modelsTransformActModelVOs(repositoryService.createModelQuery().list());
    }

    @Override
    public PageInfo<ActModelVO> pageFindModels(ActModelPageQuery actModelPageQuery) {
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
        //模型查询参数设置
        this.modelQueryParamSet(modelQuery, actModelPageQuery);
        PageInfo<ActModelVO> actModelPageInfo = new PageInfo<>();
        actModelPageInfo.setTotal(modelQuery.count());
        List<Model> models = modelQuery.listPage((actModelPageQuery.getPageNum() - 1) * actModelPageQuery.getPageSize(), actModelPageQuery.getPageSize());
        actModelPageInfo.setList(this.modelsTransformActModelVOs(models));
        return actModelPageInfo;
    }

    @Override
    public ActModelVO getModel(String modelId) {
        return this.modelTransformActModelVO(repositoryService.getModel(modelId));
    }

    @Override
    public byte[] getModelEditorSource(String modelId) {
        return repositoryService.getModelEditorSource(modelId);
    }

    @Override
    @Transactional
    public void addModelEditorSource(String modelId, byte[] bytes) {
        repositoryService.addModelEditorSource(modelId, bytes);
    }

    @Override
    @Transactional
    public ActModelVO createModel(CreateModelDTO createModelVo) throws Exception {
        String name = createModelVo.getModelName();
        String key = createModelVo.getModelKey();
        String description = createModelVo.getModelDescription();
        String category = createModelVo.getModelCategory();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");

        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        description = StringUtils.defaultString(description);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);

        Model modelData = repositoryService.newModel();
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(name);
        modelData.setKey(StringUtils.defaultString(key));
        modelData.setCategory(category);
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        return this.modelTransformActModelVO(modelData);
    }

    @Override
    @Transactional
    public void addModelEditorSourceExtra(String modelId, byte[] bytes) {
        repositoryService.addModelEditorSourceExtra(modelId, bytes);
    }

    @Override
    @Transactional
    public void deleteModel(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    @Override
    public ActModelResourceDTO getModelBpmnPng(String modelId) throws IOException {
        ActModelVO modelData = this.getModel(modelId);
        ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(this.getModelEditorSource(modelData.getActModelId()));
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(model,
                "png",
                Collections.<String>emptyList(), Collections.<String>emptyList(),
                "WenQuanYi Micro Hei", "WenQuanYi Micro Hei",
                null, null, 1.0);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return new ActModelResourceDTO(modelData.getActModelKey(), ".png", bytes);
    }

    @Override
    public ActModelResourceDTO getModelBpmnXML(String modelId) throws IOException {
        ActModelVO modelData = this.getModel(modelId);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        byte[] modelEditorSource = this.getModelEditorSource(modelData.getActModelId());
        JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] exportBytes = xmlConverter.convertToXML(bpmnModel);
        return new ActModelResourceDTO(modelData.getActModelKey(), ".bpmn20.xml", exportBytes);
    }

    @Override
    public List<ActModelNodeInfoVo> getActModelNodes(String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        List<ActModelNodeInfoVo> lists = new ArrayList<>(flowElements.size());
        ActModelNodeInfoVo actModelNodeInfoVo;
        for (FlowElement e : flowElements) {
            actModelNodeInfoVo = new ActModelNodeInfoVo(e.getId(), e.getName(), e.getDocumentation());
            lists.add(actModelNodeInfoVo);
        }
        return lists;
    }

    @Override
    @Transactional
    public ActDeploymentVO modelDeploy(String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getKey() + ".bpmn20.xml";
            Deployment deploy = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
            //设置流程分类
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list();
            for (ProcessDefinition processDefinition : list) {
                actProcessService.setProcessCategory(new SetProcessCategoryDTO(processDefinition.getId(), modelData.getCategory()));
            }
            return new ActDeploymentVO(deploy.getId(), deploy.getName(), deploy.getDeploymentTime(), deploy.getCategory(), deploy.getTenantId());
        } catch (Exception e) {
            log.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return null;
    }

    private List<ActModelVO> modelsTransformActModelVOs(List<Model> models) {
        if (null != models && !models.isEmpty()) {
            List<ActModelVO> actModelVOs = new ArrayList<>(models.size());
            for (Model model : models) {
                actModelVOs.add(this.modelTransformActModelVO(model));
            }
            return actModelVOs;
        }
        return null;
    }

    private void modelQueryParamSet(ModelQuery modelQuery, ActModelPageQuery actModelPageQuery) {
        if (StringUtils.isNotEmpty(actModelPageQuery.getModelCategory())) {
            modelQuery.modelCategory(actModelPageQuery.getModelCategory());
        }
        if (StringUtils.isNotEmpty(actModelPageQuery.getModelId())) {
            modelQuery.modelId(actModelPageQuery.getModelId());
        }
        if (StringUtils.isNotEmpty(actModelPageQuery.getModelKey())) {
            modelQuery.modelKey(actModelPageQuery.getModelKey());
        }
        if (StringUtils.isNotEmpty(actModelPageQuery.getModelName())) {
            modelQuery.modelNameLike("%" + actModelPageQuery.getModelName() + "%");
        }
    }

    private ActModelVO modelTransformActModelVO(Model model) {
        ActModelVO actModelVO = new ActModelVO();
        actModelVO.setActModelId(model.getId());
        actModelVO.setActModelKey(model.getKey());
        actModelVO.setActModelName(model.getName());
        actModelVO.setActModelCategory(model.getCategory());
        actModelVO.setActModelDeploymentId(model.getDeploymentId());
        actModelVO.setActModelCreateTime(model.getCreateTime());
        actModelVO.setActModelLastUpdateTime(model.getLastUpdateTime());
        actModelVO.setActModelVersion(model.getVersion());
        actModelVO.setActModelMetaInfo(model.getMetaInfo());
        actModelVO.setActModelTenantId(model.getTenantId());
        actModelVO.setHasEditorSource(model.hasEditorSource());
        actModelVO.setHasEditorSourceExtra(model.hasEditorSourceExtra());
        return actModelVO;
    }
}
