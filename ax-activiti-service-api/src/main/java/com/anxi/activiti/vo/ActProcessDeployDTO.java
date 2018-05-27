package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * act 流程部署 DTO
 * Created by LJ on 2018/7/17
 */
@Data
public class ActProcessDeployDTO implements Serializable{

    private String category;

    private String fileName;

    private byte[] fileContentBytes;

}
