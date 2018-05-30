package com.anxi.activiti.act.rest.editor.model;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;

/**
 * Created by LJ on 2018/3/27
 */
public interface ActModelEditorJsonRestResource {

    String getEditorJson(String modelId);

    String getEditorJsonByProcDefId(String procDefId) throws XMLStreamException, UnsupportedEncodingException;

}
