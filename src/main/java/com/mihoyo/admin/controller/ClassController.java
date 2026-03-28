package com.mihoyo.admin.controller;

import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.ClassCreateDTO;
import com.mihoyo.admin.dto.ClassDTO;
import com.mihoyo.admin.service.ClassService;
import com.mihoyo.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/my")
    public Result<List<ClassDTO>> getMyClasses(@RequestHeader("Authorization") String token) {

        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        String currentTeacherId = (String) claims.get("id");

        List<ClassDTO> list = classService.getMyClasses(currentTeacherId);
        return Result.success(list);
    }

    @PostMapping
    public Result<ClassDTO> createClass(@RequestHeader("Authorization") String token,
                                        @RequestBody ClassCreateDTO dto) {
        // 解析真实身份
        String realJwt = token.replace("Bearer ", "");
        Claims claims = JwtUtils.parseToken(realJwt);
        String currentTeacherId = (String) claims.get("id");

        // 🌟 极其霸气的防伪造操作：无视前端 DTO 里的 teacherId，强制覆盖为真实 ID！
        dto.setTeacherId(currentTeacherId);

        ClassDTO createdClass = classService.createClass(dto);
        return Result.success(createdClass);
    }
}
