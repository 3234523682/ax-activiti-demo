package com.anxi.activiti.constant;

import lombok.Getter;

/**
 * act 审核类型
 * Created by LJ on 2018/4/9
 */
@Getter
public enum ActAuditType {

    /**
     * 同意
     */
    consent("同意", "consent"),

    /**
     * 驳回
     */
    reject("驳回", "reject"),;

    private String name;

    private String value;

    ActAuditType(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
