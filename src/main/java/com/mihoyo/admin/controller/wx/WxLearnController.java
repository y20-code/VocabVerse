package com.mihoyo.admin.controller.wx;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.FinishTaskDTO;
import com.mihoyo.admin.dto.WordRecordDTO;
import com.mihoyo.admin.service.impl.WxLearnService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/wx/learn")
public class WxLearnController {

    @Autowired
    private WxLearnService wxLearnService;

    // 极其机密的武器：扒开 Token 获取真实学生 ID
    private String getStudentIdFromToken(String token) {
        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        return (String) claims.get("id");
    }

    // 💥 接口 1：拉取学习大礼包 (GET)
    @GetMapping("/task")
    public Result<Map<String, Object>> getTaskData(
            @RequestHeader("Authorization") String token,
            @RequestParam("assignmentId") String assignmentId,
            @RequestParam(value = "mode", defaultValue = "task") String mode) {

        String studentId = getStudentIdFromToken(token);
        Map<String, Object> data = wxLearnService.getLearningTask(assignmentId, studentId, mode);
        return Result.success(data);
    }

    // 💥 接口 2：记录单个单词对错 (POST)
    @PostMapping("/record")
    public Result<Void> recordWord(
            @RequestHeader("Authorization") String token,
            @RequestBody WordRecordDTO dto) {

        String studentId = getStudentIdFromToken(token);
        wxLearnService.recordWordStats(dto, studentId);
        return Result.success();
    }

    // 💥 接口 3：通关打卡！(POST)
    @PostMapping("/finish")
    public Result<Void> finishTask(
            @RequestHeader("Authorization") String token,
            @RequestBody FinishTaskDTO dto) {

        // MVP 阶段：只要触发了这个接口，前端就已经提示成功了
        // 未来你可以在这里加上给学生发积分、更新总进度的牛逼逻辑！
        System.out.println("🎉 恭喜！学生 " + getStudentIdFromToken(token) + " 完成了作业: " + dto.getAssignmentId());
        return Result.success();
    }
}
