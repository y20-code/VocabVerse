package com.yls.vocabverse.controller;

import com.yls.vocabverse.dto.UserRegistrationRequest;
import com.yls.vocabverse.entity.User;
import com.yls.vocabverse.service.UserService;
import com.yls.vocabverse.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 用户认证模块的控制器 (Controller)。
 * 作为认证流程的HTTP入口，负责处理用户注册、登录等外部请求，并将它们转换为内部的业务逻辑调用。
 *
 * @author 杨林森
 * @since 1.0.0
 *
 */

// @Tag 注解用于在 Swagger UI 中给 Controller 分组
@Tag(name ="1.用户认证模块" ,description = "包括用户注册、登录等接口")
@RestController
@RequestMapping("/api/v1/auth") // 所有认证相关接口的基础路径
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册API端点。
     * 接收包含用户名、密码和角色的JSON数据，创建一个新用户。
     *
     * @param request 包含用户名、密码和角色的注册请求数据传输对象 (DTO)。
     * @return 如果注册成功，返回HTTP 200 OK及成功消息。
     *         如果用户名已存在，返回HTTP 400 Bad Request及错误信息。
     */
    @Operation(summary = "用户注册" ,description = "提供用户名、密码和角色进行注册")
    @PostMapping("/register")
    public ResponseEntity<Result<?>> registerUser(@RequestBody UserRegistrationRequest request){
        // Controller层的职责：参数转换、调用服务、异常转译
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());
            userService.register(user);
            return ResponseEntity.ok(Result.success("注册成功！"));
        }catch (RuntimeException e){
            // 将业务异常转换为对客户端友好的HTTP错误响应
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400,e.getMessage()));
        }

    }

    /**
     * 用户登录API端点。
     * 接收用户名和密码进行验证。验证成功后，返回一个用于后续身份验证的JWT Token。
     *
     * @param request 包含登录所需用户名和密码的数据传输对象(DTO)。
     * @return 成功时返回HTTP 200 OK及包含JWT Token的数据。
     *         失败时返回HTTP 401 Unauthorized及错误信息。
     */
    @Operation(summary = "用户登录",description = "提供用户名、密码")
    @PostMapping("/login")
    public Result<?> loginUser(@RequestBody UserRegistrationRequest request){
        try{
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());


            String jwtToken = userService.login(user);

            HashMap<Object, Object> data = new HashMap<>();
            data.put("token", jwtToken);
            return Result.success(data);
        } catch (RuntimeException e){
            return Result.error(401,e.getMessage());
        }

    }



}
