package com.yls.vocabverse.entity;

import lombok.Data;
import java.time.LocalDateTime; // 引入 Java 8 的日期时间 API

/**
 * 用户实体类 (Entity)
 * 与数据库中的 `user` 表一一对应。
 *
 * @author 杨林森
 */
@Data
public class User {

    /**
     * 用户ID，主键，对应数据库的 BIGINT 类型
     */
    private Long id;

    /**
     * 用户名，唯一，用于登录
     */
    private String username;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户角色 (例如: "STUDENT", "TEACHER")
     */
    private String role;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    /**
     * 记录最后更新时间
     */
    private LocalDateTime updateTime;
}