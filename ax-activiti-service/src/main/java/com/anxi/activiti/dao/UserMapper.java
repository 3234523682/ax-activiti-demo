package com.anxi.activiti.dao;

import com.anxi.activiti.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author linjing
 */
public interface UserMapper {

    /**
     * 获取所有用户
     *
     * @return 用户集合列表
     */
    @Select("select * from t_user")
    List<User> getAll();

    /**
     * 新添用户
     *
     * @param user 用户信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into t_user(user_name,password,phone) values(#{userName},#{password},#{phone})")
    void install(User user);

}
