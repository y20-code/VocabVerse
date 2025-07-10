package com.yls.vocabverse.mapper;

import com.yls.vocabverse.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username= #{username}")
    User findByUsername(String username);

    @Insert("insert into user(username,password,role,create_time,update_time) " +
            "values (#{username},#{password},#{role},NOW(),NOW())")
    void insert(User user);
}
