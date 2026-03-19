package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.StudentListResponseDTO;

public interface StudentService {

    StudentListResponseDTO getTeacherStudentList(String teacherId);
}
