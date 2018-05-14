package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/26
 */
@Data
public class SetProcessStateDTO implements Serializable {

    /**
     * act 流程ID
     */
    private String procDefId;

    /**
     * 是否激活
     */
    private boolean isActivate;

    public SetProcessStateDTO(String procDefId, boolean isActivate) {
        this.procDefId = procDefId;
        this.isActivate = isActivate;
    }
}
