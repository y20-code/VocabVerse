package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.SimpleClassDTO;
import com.mihoyo.admin.dto.StudentListItemDTO;
import com.mihoyo.admin.dto.StudentListResponseDTO;
import com.mihoyo.admin.entity.ClassEntity;
import com.mihoyo.admin.mapper.DashboardMapper;
import com.mihoyo.admin.mapper.StudentMapper;
import com.mihoyo.admin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public StudentListResponseDTO getTeacherStudentList(String teacherId) {

        StudentListResponseDTO studentListResponseDTO = new StudentListResponseDTO();

        List<ClassEntity> classEntities = dashboardMapper.selectClassesByTeacherId(teacherId);

        if (classEntities == null || classEntities.isEmpty()) {
            studentListResponseDTO.setTotalStudents(0);
            studentListResponseDTO.setAvgProgress(0);
            studentListResponseDTO.setClasses(new ArrayList<>());
            studentListResponseDTO.setStudents(new ArrayList<>());
            return studentListResponseDTO;
        }

        List<String> ClassIds = classEntities.stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toList());


        List<SimpleClassDTO> simpleClasses = classEntities.stream().map(c -> {
            SimpleClassDTO dto = new SimpleClassDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());
            return dto;
        }).collect(Collectors.toList());

        List<StudentListItemDTO> students = studentMapper.selectTeacherStudentsList(ClassIds);

        studentListResponseDTO.setClasses(simpleClasses);
        studentListResponseDTO.setStudents(students);
        studentListResponseDTO.setTotalStudents(students.size());

        studentListResponseDTO.setAvgProgress(0);

        return studentListResponseDTO;
    }
}
