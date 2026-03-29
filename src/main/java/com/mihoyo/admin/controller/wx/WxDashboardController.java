package com.mihoyo.admin.controller.wx;

import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.WxDashboardHomeDTO;
import com.mihoyo.admin.service.impl.WxDashboardService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wx/dashboard")
public class WxDashboardController {

    @Autowired
    private WxDashboardService wxDashboardService;

    @GetMapping("/home")
    public Result<WxDashboardHomeDTO> getHomeData(@RequestHeader("Authorization") String token) {

        // 1. 极其无情地扒开 Token 拿到学生真实 ID (防越权！)
        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        String studentId = (String) claims.get("id");

        // 2. 呼叫大脑执行计算
        WxDashboardHomeDTO data = wxDashboardService.getHomeData(studentId);

        return Result.success(data);
    }
}
