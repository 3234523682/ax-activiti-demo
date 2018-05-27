package com.anxi.activiti.service.impl;

import com.anxi.activiti.constant.ActProcessResourceType;
import com.anxi.activiti.service.api.ActProcessService;
import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessDeployDTO;
import com.anxi.activiti.vo.ActProcessInsVO;
import com.anxi.activiti.vo.ActProcessInstanceQuery;
import com.anxi.activiti.vo.DeleteProcInsDTO;
import com.anxi.activiti.vo.ProcessResourceReadDTO;
import com.anxi.activiti.vo.SetProcessCategoryDTO;
import com.anxi.activiti.vo.SetProcessStateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by LJ on 2018/3/26
 */
@Slf4j
@Service("actProcessService")
public class ActProcessServiceImpl implements ActProcessService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Override
    public PageInfo<ActProcessDefinitionVO> processDefinitionQuery(ActProcessDefinitionQuery queryParam) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion().orderByProcessDefinitionKey().asc();
        this.processDefinitionQuerySet(processDefinitionQuery, queryParam);
        PageInfo<ActProcessDefinitionVO> actProcessPageInfo = new PageInfo<>();
        actProcessPageInfo.setTotal(processDefinitionQuery.count());
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((queryParam.getPageNum() - 1) * queryParam.getPageSize(), queryParam.getPageSize());
        List<ActProcessDefinitionVO> list = new ArrayList<>(processDefinitionList.size());
        ActProcessDefinitionVO actProcess;
        Deployment deployment;
        for (ProcessDefinition processDefinition : processDefinitionList) {
            deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
            actProcess = new ActProcessDefinitionVO();
            actProcess.setProcDefId(processDefinition.getId());
            actProcess.setProcDefKey(processDefinition.getKey());
            actProcess.setCategory(processDefinition.getCategory());
            actProcess.setProcDefName(processDefinition.getName());
            actProcess.setProcDefVersion(processDefinition.getVersion());
            actProcess.setDeploymentId(processDefinition.getDeploymentId());
            actProcess.setProcDefDeployTime(deployment.getDeploymentTime());
            actProcess.setSuspended(processDefinition.isSuspended());
            actProcess.setProcDefResourceXml(processDefinition.getResourceName());
            actProcess.setProcDefResourceImg(processDefinition.getDiagramResourceName());
            list.add(actProcess);
        }
        actProcessPageInfo.setList(list);
        return actProcessPageInfo;
    }

    @Override
    public PageInfo<ActProcessInsVO> processInstanceList(ActProcessInstanceQuery queryParam) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        this.processInstanceQuerySet(processInstanceQuery, queryParam);
        PageInfo<ActProcessInsVO> actProcessInstancePageInfo = new PageInfo<>();
        actProcessInstancePageInfo.setTotal(processInstanceQuery.count());
        List<ProcessInstance> processInstances = processInstanceQuery.listPage(queryParam.getPageNum() * queryParam.getPageSize(), queryParam.getPageSize());
        List<ActProcessInsVO> list = new ArrayList<>(processInstances.size());
        ActProcessInsVO actProcessIns;
        Task task;
        for (ProcessInstance instance : processInstances) {
            actProcessIns = new ActProcessInsVO(instance.getId(), instance.getProcessInstanceId(), instance.getName(), instance.getProcessDefinitionId(), instance.getActivityId(), instance.isSuspended());
            task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
            actProcessIns.setActivityName(task.getName());
            actProcessIns.setActivityConductor(task.getAssignee());
            list.add(actProcessIns);
        }
        actProcessInstancePageInfo.setList(list);
        return actProcessInstancePageInfo;
    }

    @Override
    @Transactional
    public void setProcessCategory(SetProcessCategoryDTO updateProcessCategory) {
        repositoryService.setProcessDefinitionCategory(updateProcessCategory.getProcDefId(), updateProcessCategory.getCategory());
    }

    @Override
    @Transactional
    public void setProcessState(SetProcessStateDTO setProcessState) {
        String procDefId = setProcessState.getProcDefId();
        if (setProcessState.isActivate()) {
            repositoryService.activateProcessDefinitionById(procDefId, true, null);
            log.info("已激活ID为[" + procDefId + "]的流程定义。");
        } else {
            repositoryService.suspendProcessDefinitionById(procDefId, true, null);
            log.info("已挂起ID为[" + procDefId + "]的流程定义。");
        }
    }

    @Override
    @Transactional
    public boolean processDeploy(ActProcessDeployDTO actProcessDeployDTO) {
        try {
            String fileName = actProcessDeployDTO.getFileName();
            String extension = FilenameUtils.getExtension(fileName);
            String category = actProcessDeployDTO.getCategory();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(actProcessDeployDTO.getFileContentBytes());
            Deployment deployment;
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(byteArrayInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (fileName.contains("bpmn20.xml")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, byteArrayInputStream).deploy();
            } else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", byteArrayInputStream).deploy();
            } else {
                log.error("不支持的文件类型：{}", extension);
                return false;
            }
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                log.info("部署成功，流程ID=" + processDefinition.getId());
            }

            if (list.size() == 0) {
                log.error("部署失败，没有流程。");
                return false;
            }

        } catch (Exception e) {
            throw new ActivitiException("部署失败！", e);
        }
        return true;
    }

    @Override
    @Transactional
    public void convertToModel(String procDefId) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        org.activiti.engine.repository.Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getName());
        modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
        modelData.setDeploymentId(processDefinition.getDeploymentId());
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count() + 1)));

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
    }

    @Override
    public byte[] actProcessResourceRead(ProcessResourceReadDTO processResourceReadDTO) throws IOException {
        String procDefId = processResourceReadDTO.getProcDefId();
        String proInsId = processResourceReadDTO.getProInsId();
        String resourceType = processResourceReadDTO.getResourceType();
        if (StringUtils.isBlank(procDefId)) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
            procDefId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        String resourceName = "";
        if (ActProcessResourceType.IMAGE.equals(resourceType)) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (ActProcessResourceType.XML.equals(resourceType)) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc;
        while ((rc = resourceAsStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    @Override
    @Transactional
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    @Transactional
    public void deleteProcIns(DeleteProcInsDTO deleteProcIns) {
        runtimeService.deleteProcessInstance(deleteProcIns.getProcInsId(), deleteProcIns.getDeleteReason());
    }

    private void processDefinitionQuerySet(ProcessDefinitionQuery processDefinitionQuery, ActProcessDefinitionQuery queryParam) {
        if (StringUtils.isNotEmpty(queryParam.getProcDefKey())) {
            processDefinitionQuery.processDefinitionKey(queryParam.getProcDefKey());
        }
        if (StringUtils.isNotEmpty(queryParam.getProcDefName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + queryParam.getProcDefName() + "%");
        }
        if (StringUtils.isNotEmpty(queryParam.getCategory())) {
            processDefinitionQuery.processDefinitionCategory(queryParam.getCategory());
        }
    }

    private void processInstanceQuerySet(ProcessInstanceQuery processInstanceQuery, ActProcessInstanceQuery queryParam) {
        if (StringUtils.isNotBlank(queryParam.getProcInsId())) {
            processInstanceQuery.processInstanceId(queryParam.getProcInsId());
        }
        if (StringUtils.isNotBlank(queryParam.getProcInsName())) {
            processInstanceQuery.processInstanceNameLike("%" + queryParam.getProcInsName() + "%");
        }
        if (StringUtils.isNotBlank(queryParam.getProcDefKey())) {
            processInstanceQuery.processDefinitionKey(queryParam.getProcDefKey());
        }
    }

}
