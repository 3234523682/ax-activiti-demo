package com.anxi.activiti.service.impl;

import com.anxi.activiti.dao.ActCustomByteArrayMapper;
import com.anxi.activiti.model.ActCustomByteArrayEntity;
import com.anxi.activiti.service.api.ActCustomByteArrayService;
import com.anxi.activiti.vo.ActByteArrayQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by LJ on 2018/5/28
 */
@Service("actCustomByteArrayService")
public class ActCustomByteArrayServiceImpl implements ActCustomByteArrayService {

    @Resource
    private ActCustomByteArrayMapper actCustomByteArrayMapper;

    @Override
    public List<ActCustomByteArrayEntity> findByDeploymentId(String deploymentId) {
        return actCustomByteArrayMapper.findByDeploymentId(deploymentId);
    }

    @Override
    public ActCustomByteArrayEntity findByDeployIdAndName(ActByteArrayQuery actByteArrayQuery) {
        List<ActCustomByteArrayEntity> lists = actCustomByteArrayMapper.findByDeployIdAndName(actByteArrayQuery);
        return null == lists || lists.isEmpty() ? null : lists.get(0);
    }
}
