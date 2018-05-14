package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActGroupQuery extends CommonQuery implements Serializable {

    private String groupId;

    private String groupName;

    private String groupType;

    private String userId;
}
