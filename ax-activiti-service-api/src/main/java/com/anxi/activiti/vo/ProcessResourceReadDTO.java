package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * act 流程资源读取 DTO
 * Created by LJ on 2018/5/3
 */
@Data
public class ProcessResourceReadDTO implements Serializable {

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 流程实例ID
     */
    private String proInsId;

    /**
     * 资源类型(xml|image)
     */
    private String resourceType;
}
