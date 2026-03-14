package com.mihoyo.admin.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private String id;
    private String institutionId;
    private String loginAccount;
    private String password;
    private String fullName;
    private String role;
    private String customAvatar;
    private LocalDateTime createdAt; // 对应数据库的 DATETIME
    private String title;
    private String grade;
    private String classId;
    private String parentId;

    private String token;
}
