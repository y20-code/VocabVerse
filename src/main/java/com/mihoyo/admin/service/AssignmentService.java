package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.CreateAssignmentReqDTO;

public interface AssignmentService {

    void createAssignments(CreateAssignmentReqDTO reqDTO,String teacherId);
}
