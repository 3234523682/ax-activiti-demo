package com.anxi.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActUserQuery extends CommonQuery implements Serializable {

    private String userId;

    private String groupId;

    private String userName;

    private String userEmail;
}
