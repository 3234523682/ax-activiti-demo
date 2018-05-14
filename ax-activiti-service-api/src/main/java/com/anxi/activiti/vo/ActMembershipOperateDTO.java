package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/8
 */
@Data
public class ActMembershipOperateDTO implements Serializable {

    private String userId;

    private String[] groupIds;
}
