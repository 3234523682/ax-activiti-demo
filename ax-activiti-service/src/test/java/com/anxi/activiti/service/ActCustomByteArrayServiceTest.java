package com.anxi.activiti.service;

import com.anxi.activiti.model.ActCustomByteArrayEntity;
import com.anxi.activiti.service.api.ActCustomByteArrayService;
import com.anxi.activiti.vo.ActByteArrayQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by LJ on 2018/5/29
 */
@Slf4j
public class ActCustomByteArrayServiceTest extends BaseJunit4Test {

    @Resource
    private ActCustomByteArrayService actCustomByteArrayService;

    @Test
    public void findByDeployIdAndName() {
        ActCustomByteArrayEntity byDeployIdAndName = actCustomByteArrayService.findByDeployIdAndName(new ActByteArrayQuery("test_001.bpmn20.xml", "25008"));
        log.info("===================== byteStr：{}", byDeployIdAndName.getBytesStr());
    }
}