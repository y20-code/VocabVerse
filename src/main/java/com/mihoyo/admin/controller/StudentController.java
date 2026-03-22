package com.mihoyo.admin.controller;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.StudentListResponseDTO;
import com.mihoyo.admin.service.StudentService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "学生管理接口", description = "处理学生大名单、学情分析等核心业务")
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "获取当前老师名下所有学生大名单", description = "解析 Token 获取 teacherId，连表查询学生、词汇量及活跃状态")
    @GetMapping
    public Result<StudentListResponseDTO> getStudentList(@RequestHeader("Authorization") String token) {




        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);

        String currentTeacherId = (String) claims.get("id");

        StudentListResponseDTO responseData = studentService.getTeacherStudentList(currentTeacherId);


        return Result.success(responseData);
    }
}
