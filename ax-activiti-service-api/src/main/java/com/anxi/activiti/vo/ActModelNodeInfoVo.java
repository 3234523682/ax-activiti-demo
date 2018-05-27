package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * act 模型节点信息
 * Created by LJ on 2018/5/25
 */
@Data
public class ActModelNodeInfoVo implements Serializable {

    private String nodeId;

    private String nodeName;

    private String nodeDescribe;

    public ActModelNodeInfoVo() {
    }

    public ActModelNodeInfoVo(String nodeId, String nodeName, String nodeDescribe) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.nodeDescribe = nodeDescribe;
    }
}
