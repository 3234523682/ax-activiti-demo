package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/8
 */
@Data
public class ActGroupSaveDTO implements Serializable {

    private String groupId;

    private String groupName;

    private String groupType;
}
