package com.yls.vocabverse.controller;

import com.yls.vocabverse.dto.UserRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证模块的控制器 (Controller)。
 * 本类负责处理所有与用户身份认证相关的HTTP请求，例如用户注册和登录。
 *
 * @author 杨林森
 */

// @Tag 注解用于在 Swagger UI 中给 Controller 分组
@Tag(name ="1.用户认证模块" ,description = "包括用户注册、登录等接口")
@RestController
@RequestMapping("/api/v1/auth") // 所有认证相关接口的基础路径
public class AuthController {

    /**
     * 处理用户注册请求的API端点。
     *
     * @param request 包含用户名、密码和角色的注册请求数据传输对象 (DTO)。
     * @return 返回一个标准响应实体，表示操作结果。
     */
    @Operation(summary = "用户注册" ,description = "提供用户名、密码和角色进行注册")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request){
        System.out.println("接收到注册请求:" + request);

        // TODO: 在这里注入 UserService 并调用其 register 方法来处理真正的业务逻辑。



        return ResponseEntity.ok("注册接口调用成功！");
    }

}
