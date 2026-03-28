package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class StudentBatchCreateDTO {

    private String loginAccount; // 小程序登录账号
    private String password;     // 初始密码
    private String fullName;     // 学生姓名
    private String role;         // 角色 (student)
    private String classId;      // 绑定的班级 ID
    private String grade;        // 年级
}
