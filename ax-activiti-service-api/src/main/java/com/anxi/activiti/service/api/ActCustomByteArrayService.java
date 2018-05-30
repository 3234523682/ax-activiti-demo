package com.anxi.activiti.service.api;

import com.anxi.activiti.model.ActCustomByteArrayEntity;
import com.anxi.activiti.vo.ActByteArrayQuery;

import java.util.List;

/**
 * Created by LJ on 2018/5/28
 */
public interface ActCustomByteArrayService {

    List<ActCustomByteArrayEntity> findByDeploymentId(String deploymentId);

    ActCustomByteArrayEntity findByDeployIdAndName(ActByteArrayQuery actByteArrayQuery);
}
