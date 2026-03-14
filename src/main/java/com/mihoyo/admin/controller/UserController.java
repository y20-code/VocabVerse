package com.mihoyo.admin.controller;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.LoginDTO;
import com.mihoyo.admin.dto.RegisterDTO;
import com.mihoyo.admin.entity.User;
import com.mihoyo.admin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "1. 认证与授权模块", description = "负责用户的登录、登出与权限校验")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户账号密码登录", description = "供教师和学生使用系统账号密码登录系统")
    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginDTO loginDTO){
        try {
            User user = userService.login(loginDTO);

            return Result.success(user);
        } catch (RuntimeException e) {

            return Result.error(401,e.getMessage());
        }
    }

    @Operation(summary = "用户账号注册", description = "供新用户注册系统账号，校验重名并自动生成默认昵称")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        try{
            userService.register(registerDTO);
            return Result.success();
        } catch (RuntimeException e){
            return Result.error(401,e.getMessage());
        }
    }
}
