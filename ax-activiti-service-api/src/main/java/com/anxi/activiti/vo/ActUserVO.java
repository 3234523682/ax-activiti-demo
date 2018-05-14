package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LJ on 2018/5/8
 */
@Data
public class ActUserVO implements Serializable {

    private String userId;

    private String userName;

    private String userEmail;

    private List<ActGroupVO> actGroupVOs;

    public ActUserVO() {
    }

    public ActUserVO(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
