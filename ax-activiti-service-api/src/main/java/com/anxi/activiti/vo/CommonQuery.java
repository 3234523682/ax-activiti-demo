package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/3/29
 */
@Data
class CommonQuery implements Serializable {

    /**
     * 当期页码
     */
    int pageNum = 1;

    /**
     * 数量大小
     */
    int pageSize = 10;

    CommonQuery() {
    }

    CommonQuery(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
