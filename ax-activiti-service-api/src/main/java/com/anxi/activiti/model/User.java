package com.anxi.activiti.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author linjing
 */
@Data
public class User implements Serializable {

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    public User() {
    }

    public User(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

}
