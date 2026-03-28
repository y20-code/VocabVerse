package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.ClassCreateDTO;
import com.mihoyo.admin.dto.ClassDTO;

import java.util.List;

public interface ClassService {

    List<ClassDTO> getMyClasses(String teacherId);

    ClassDTO createClass(ClassCreateDTO dto);
}
