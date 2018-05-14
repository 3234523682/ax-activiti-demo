package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/26
 */
@Data
public class DeleteProcInsDTO implements Serializable {

    /**
     * act 流程实例Id
     */
    private String procInsId;

    /**
     * 删除原因，可为空
     */
    private String deleteReason;

    public DeleteProcInsDTO() {
    }

    public DeleteProcInsDTO(String procInsId, String deleteReason) {
        this.procInsId = procInsId;
        this.deleteReason = deleteReason;
    }
}
