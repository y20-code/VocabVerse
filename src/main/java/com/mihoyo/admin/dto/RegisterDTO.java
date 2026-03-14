package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String loginAccount;
    private String password;
    private String role;

}
