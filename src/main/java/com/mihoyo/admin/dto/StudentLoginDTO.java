package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class StudentLoginDTO {

    private String loginAccount; // 小程序账号 (例如 s12345)
    private String password;     // 密码
}
