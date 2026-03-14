package com.mihoyo.admin.service;


import com.mihoyo.admin.dto.LoginDTO;
import com.mihoyo.admin.dto.RegisterDTO;
import com.mihoyo.admin.entity.User;

public interface UserService {

    /**
     * 执行登录核心逻辑
     * @param loginDTO 前端传来的登录数据包
     * @return 校验成功后，返回脱敏后的用户信息
     */
    User login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
