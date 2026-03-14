package com.mihoyo.admin.mapper;

import com.mihoyo.admin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 用户表数据库操作接口
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM Users WHERE loginAccount = #{loginAccount}")
    User selectByLoginAccount(String loginAccount);

    @Insert("INSERT INTO Users(id,loginAccount,password,fullName,role) " +
            "VALUES (#{id}, #{loginAccount}, #{password}, #{fullName}, #{role})")
    int InsertUser(User user);
}
