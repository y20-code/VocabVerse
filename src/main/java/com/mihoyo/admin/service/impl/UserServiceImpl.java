package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.LoginDTO;
import com.mihoyo.admin.dto.RegisterDTO;
import com.mihoyo.admin.entity.User;
import com.mihoyo.admin.mapper.UserMapper;
import com.mihoyo.admin.service.UserService;
import com.mihoyo.admin.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(LoginDTO loginDTO) {

        User user = userMapper.selectByLoginAccount(loginDTO.getLoginAccount());

        if (user == null){
            throw new RuntimeException("账号或密码错误");
        }

        if(!"teacher".equals(user.getRole())){
            throw new RuntimeException("越权访问：该账号无管理后台权限！");
        }

        String realToken = JwtUtils.generateToken(user.getLoginAccount(), user.getRole());

        user.setToken(realToken);
        user.setPassword(null);

        return user;
    }

    @Override
    public void register(RegisterDTO registerDTO) {

        User existUser  = userMapper.selectByLoginAccount(registerDTO.getLoginAccount());

        if (existUser != null) {
            throw new RuntimeException("该账号已被注册了");
        }

        User user = new User();

        user.setLoginAccount(registerDTO.getLoginAccount());
        user.setPassword(registerDTO.getPassword());
        user.setRole(registerDTO.getRole());

        String uuid = java.util.UUID.randomUUID().toString();
        String cleanUuid = uuid.replaceAll("-", "");

        user.setId(uuid);
        user.setFullName("用户_" + cleanUuid.substring(0,8));

        userMapper.InsertUser(user);
    }
}
