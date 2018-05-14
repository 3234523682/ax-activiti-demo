package com.anxi.activiti.service.api;


import com.anxi.activiti.vo.ActDeploymentVO;
import com.anxi.activiti.vo.ActModelPageQuery;
import com.anxi.activiti.vo.ActModelVO;
import com.anxi.activiti.vo.CreateModelDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by LJ on 2018/3/22
 */
public interface ActRepositoryService {

    List<ActModelVO> findAllModels();

    PageInfo<ActModelVO> pageFindModels(ActModelPageQuery actModelPageQuery);

    ActModelVO getModel(String modelId);

    byte[] getModelEditorSource(String modelId);

    void addModelEditorSource(String modelId, byte[] bytes);

    ActModelVO createModel(CreateModelDTO createModelVo) throws Exception;

    void addModelEditorSourceExtra(String modelId, byte[] bytes);

    void deleteModel(String modelId);

    /**
     * 模型部署流程
     *
     * @param modelId 模型ID
     */
    ActDeploymentVO modelDeploy(String modelId);
}
