package com.yls.vocabverse.service.impl;

import com.yls.vocabverse.entity.User;
import com.yls.vocabverse.mapper.UserMapper;
import com.yls.vocabverse.service.UserService;
import com.yls.vocabverse.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    //用户注册
    public void register(User user) {
        User byUsername = userMapper.findByUsername(user.getUsername());
        if (byUsername != null) {
            throw new RuntimeException("用户名 '" + user.getUsername() + "'已经被注册了");
        }

        String encodePassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodePassword);

        userMapper.insert(user);

    }


    // 用户登录
    public String login(User user) {

        User byUsername = userMapper.findByUsername(user.getUsername());

        if (byUsername == null) {

            throw new RuntimeException("用户不存在！");
        }

        if (!passwordEncoder.matches(user.getPassword(), byUsername.getPassword())) {
            throw new RuntimeException("密码错误！");
        }

        return jwtUtil.generateToken(byUsername.getId(),byUsername.getUsername(),byUsername.getRole());

    }
}
