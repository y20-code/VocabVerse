package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.StudentLoginDTO;
import com.mihoyo.admin.dto.UserAuthDTO;
import com.mihoyo.admin.mapper.UserMapper;
import com.mihoyo.admin.service.WxAuthService;
import com.mihoyo.admin.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxAuthServiceImpl implements WxAuthService {

    @Autowired
    private UserMapper userMapper;

    public String login(StudentLoginDTO dto) {
        // 1. 拿着账号去数据库捞人
        UserAuthDTO user = userMapper.selectUserByLoginAccount(dto.getLoginAccount());

        // 🛡️ 防线一：查无此人
        if (user == null) {
            throw new RuntimeException("该账号不存在，请联系老师");
        }

        // 🛡️ 防线二：跨界打击（防止老师拿自己的账号去登小程序）
        if (!"student".equals(user.getRole())) {
            throw new RuntimeException("越权警告：非学生账号禁止登录小程序端！");
        }

        // 🛡️ 防线三：密码核对 (MVP 阶段极其简单地对比明文)
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("密码错误，请重新输入");
        }


        // 生成并返回 Token 字符串
        return JwtUtils.generateToken(user.getLoginAccount(), user.getRole(), user.getId());
    }
}
