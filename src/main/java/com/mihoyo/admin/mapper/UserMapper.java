package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.UserAuthDTO;
import com.mihoyo.admin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;


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

    @Insert("INSERT INTO Users (id, loginAccount, password, fullName, role, classId, grade, createdAt) " +
            "VALUES (#{id}, #{loginAccount}, #{password}, #{fullName}, #{role}, #{classId}, #{grade}, #{createdAt})")
    void insertUser(@Param("id") String id,
                    @Param("loginAccount") String loginAccount,
                    @Param("password") String password,
                    @Param("fullName") String fullName,
                    @Param("role") String role,
                    @Param("classId") String classId,
                    @Param("grade") String grade,
                    @Param("createdAt") Date createdAt);

    // 小程序专属：根据登录账号查询用户完整凭证
    @Select("SELECT id, loginAccount, password, fullName, role FROM Users WHERE loginAccount = #{loginAccount} LIMIT 1")
    UserAuthDTO selectUserByLoginAccount(@Param("loginAccount") String loginAccount);
}
