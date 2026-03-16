package com.mihoyo.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentEntity {

    private String id;
    private String classId;
    private String title;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}
