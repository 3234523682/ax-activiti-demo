package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/28
 */
@Data
public class ActByteArrayQuery implements Serializable {

    private String name;

    private String deploymentId;

    public ActByteArrayQuery() {
    }

    public ActByteArrayQuery(String name, String deploymentId) {
        this.name = name;
        this.deploymentId = deploymentId;
    }
}
