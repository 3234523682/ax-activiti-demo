package com.anxi.activiti.service.api;

import com.anxi.activiti.vo.ActProcessDefinitionQuery;
import com.anxi.activiti.vo.ActProcessDefinitionVO;
import com.anxi.activiti.vo.ActProcessDeployDTO;
import com.anxi.activiti.vo.ActProcessInsVO;
import com.anxi.activiti.vo.ActProcessInstanceQuery;
import com.anxi.activiti.vo.DeleteProcInsDTO;
import com.anxi.activiti.vo.ProcessResourceReadDTO;
import com.anxi.activiti.vo.SetProcessCategoryDTO;
import com.anxi.activiti.vo.SetProcessStateDTO;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

/**
 * Created by LJ on 2018/3/26
 */
public interface ActProcessService {

    /**
     * 流程定义列表查询
     */
    PageInfo<ActProcessDefinitionVO> processDefinitionQuery(ActProcessDefinitionQuery actProcessDefinitionQuery);

    /**
     * 流程实例查询
     */
    PageInfo<ActProcessInsVO> processInstanceList(ActProcessInstanceQuery actProcessInstanceQuery);

    ActProcessDefinitionVO getProcessDefinitionById(String procDefId);

    /**
     * 设置流程分类
     */
    void setProcessCategory(SetProcessCategoryDTO updateProcessCategory);

    /**
     * 挂起、激活流程实例
     */
    void setProcessState(SetProcessStateDTO setProcessState);

    /**
     * 流程部署
     */
    boolean processDeploy(ActProcessDeployDTO actProcessDeployDTO);

    /**
     * 将部署的流程转换为模型
     *
     * @param procDefId 流程ID
     */
    void convertToModel(String procDefId) throws Exception;

    byte[] actProcessResourceRead(ProcessResourceReadDTO processResourceReadDTO) throws IOException;

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    void deleteDeployment(String deploymentId);

    /**
     * 删除部署的流程实例
     */
    void deleteProcIns(DeleteProcInsDTO deleteProcIns);
}
