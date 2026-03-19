package com.mihoyo.admin.controller;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.CreateAssignmentReqDTO;
import com.mihoyo.admin.service.AssignmentService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "作业管理接口", description = "处理千人千面作业的分发与管控")
@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Operation(summary = "极速批量布置作业", description = "支持多班级群发与千人千面自适应引擎")
    @PostMapping
    public Result<Void> createAssignments(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateAssignmentReqDTO reqDTO) {


        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        String currentTeacherId = (String) claims.get("id");


        assignmentService.createAssignments(reqDTO, currentTeacherId);


        return Result.success();
    }
}
