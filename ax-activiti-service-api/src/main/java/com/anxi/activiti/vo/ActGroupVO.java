package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LJ on 2018/5/8
 */
@Data
public class ActGroupVO implements Serializable {

    private String groupId;

    private String groupName;

    private String groupType;

    private List<ActUserVO> actUserVOs;

    public ActGroupVO() {
    }

    public ActGroupVO(String groupId, String groupName, String groupType) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
    }
}
