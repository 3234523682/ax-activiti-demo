package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * act 模型资源DTO
 * Created by LJ on 2018/5/17
 */
@Data
public class ActModelResourceDTO implements Serializable {

    /**
     * 资源文件名称
     */
    private String name;

    /**
     * 资源文件后缀
     */
    private String suffix;

    /**
     * 资源内容字节数组
     */
    private byte[] contentByte;

    public ActModelResourceDTO() {
    }

    public ActModelResourceDTO(String name, String suffix, byte[] contentByte) {
        this.name = name;
        this.suffix = suffix;
        this.contentByte = contentByte;
    }
}
