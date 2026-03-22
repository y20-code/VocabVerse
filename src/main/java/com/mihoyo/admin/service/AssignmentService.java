package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.ClassDTO;
import com.mihoyo.admin.dto.CreateAssignmentReqDTO;

import java.util.List;

public interface AssignmentService {

    void createAssignments(CreateAssignmentReqDTO reqDTO,String teacherId);

    List<ClassDTO> getClasses(String teacherId);
}
