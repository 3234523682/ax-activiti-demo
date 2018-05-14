package com.anxi.activiti.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LJ on 2018/5/4
 */
@Data
public class ActModelSaveDTO implements Serializable {

    private String modelId;

    private String name;

    private String description;

    private String json_xml;

    private String svg_xml;
}
