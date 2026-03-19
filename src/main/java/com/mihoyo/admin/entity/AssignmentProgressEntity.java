package com.mihoyo.admin.entity;

import lombok.Data;

@Data
public class AssignmentProgressEntity {

    private String id;
    private String assignmentId;
    private String studentId;
    private String status;    // 默认 'pending'
    private Integer progress;
}
