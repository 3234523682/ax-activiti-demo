package com.anxi.activiti.web.workflow.act.rest.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by LJ on 2018/3/28
 */
@Slf4j
public abstract class ActRestSupport {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected JsonNode readTree(String cont) {
        try {
            return objectMapper.readTree(cont);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
