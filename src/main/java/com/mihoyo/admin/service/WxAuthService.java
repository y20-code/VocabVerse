package com.mihoyo.admin.service;


import com.mihoyo.admin.dto.StudentLoginDTO;

public interface WxAuthService {

    String login(StudentLoginDTO dto);


}
