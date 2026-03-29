package com.mihoyo.admin.controller.wx;

import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.StudentLoginDTO;
import com.mihoyo.admin.service.WxAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wx/auth")
public class WXAuthController {

    @Autowired
    private WxAuthService wxAuthService;

    @PostMapping("/login")
    public Result<String> studentLogin(@RequestBody StudentLoginDTO dto) {

        if (dto.getLoginAccount() == null || dto.getPassword() == null) {
            return Result.error(400, "账号或密码不能为空");
        }

        try {
            // 呼叫大脑执行登录，成功则拿到无敌的 Token
            String token = wxAuthService.login(dto);
            // 极其丝滑地把 Token 发给学生！
            return Result.success(token);
        } catch (RuntimeException e) {
            // 登录失败（密码错、没这个人等），极其冷酷地打回
            return Result.error(401, e.getMessage());
        }
    }
}
