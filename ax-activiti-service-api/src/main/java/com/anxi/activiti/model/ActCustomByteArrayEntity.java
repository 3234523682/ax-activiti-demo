package com.anxi.activiti.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义 act 二进制数据表对象
 * Created by LJ on 2018/5/28
 */
@Data
public class ActCustomByteArrayEntity implements Serializable {

    private String id;

    private int revision;

    private String name;

    private String deploymentId;

    private String bytesStr;

}
