package com.mihoyo.admin.dto;

import lombok.Data;

//学生登录数据
@Data
public class UserAuthDTO {
    private String id;
    private String loginAccount;
    private String password;
    private String role;       // 极其关键：用来防越权
    private String fullName;
}
