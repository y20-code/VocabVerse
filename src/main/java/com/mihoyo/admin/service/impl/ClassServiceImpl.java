package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.ClassCreateDTO;
import com.mihoyo.admin.dto.ClassDTO;
import com.mihoyo.admin.mapper.ClassMapper;
import com.mihoyo.admin.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    public List<ClassDTO> getMyClasses(String teacherId) {
        return classMapper.selectClassesByTeacherId(teacherId);
    }

    public ClassDTO createClass(ClassCreateDTO dto) {
        String classId = UUID.randomUUID().toString();

        String inviteCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        classMapper.insertClass(classId, dto.getTeacherId(), dto.getName(), dto.getCurrentGrade(), inviteCode, new Date());

        // 组装刚建好的班级信息返回
        ClassDTO result = new ClassDTO();
        result.setId(classId);
        result.setName(dto.getName());
        return result;
    }
}
