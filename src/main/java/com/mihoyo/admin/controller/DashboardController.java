package com.mihoyo.admin.controller;

import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.ClassProgressDTO;
import com.mihoyo.admin.dto.DashboardStatsDTO;
import com.mihoyo.admin.service.DashboardService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Tag(name = "2. 教师看板模块", description = "负责展示教师工作台的核心统计数据")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "获取顶部统计看板数据", description = "解析当前登录教师的Token，返回其名下活跃班级数、未过期作业数及学生平均正确率")
    @GetMapping("/stats")
    public Result<DashboardStatsDTO> getDashboardStats(@RequestHeader("Authorization") String token) {


        String realJwt = token.replace("Bearer ", "");

        Claims claims = JwtUtils.parseToken(realJwt);

        String currentTeacherId = (String) claims.get("id");

        DashboardStatsDTO dashboardStats = dashboardService.getDashboardStats(currentTeacherId);

        return Result.success(dashboardStats);
    }

    @Operation(summary = "获取班级昨日作业进度", description = "获取当前老师名下班级的作业完成率及未通关名单")
    @GetMapping("/class-progress")
    public Result<List<ClassProgressDTO>> getClassProgress(@RequestHeader("Authorization") String token) {

        // 解析 JWT，拿到老师 ID
        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        String currentTeacherId = (String) claims.get("id");

        List<ClassProgressDTO> progressList = dashboardService.getClassProgress(currentTeacherId);

        return Result.success(progressList);
    }
}
