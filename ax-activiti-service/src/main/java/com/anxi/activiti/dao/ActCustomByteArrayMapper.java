package com.anxi.activiti.dao;

import com.anxi.activiti.model.ActCustomByteArrayEntity;
import com.anxi.activiti.vo.ActByteArrayQuery;

import java.util.List;

/**
 * Created by LJ on 2018/5/28
 */
public interface ActCustomByteArrayMapper {

    List<ActCustomByteArrayEntity> findByDeploymentId(String deploymentId);

    List<ActCustomByteArrayEntity> findByDeployIdAndName(ActByteArrayQuery actByteArrayQuery);

}
