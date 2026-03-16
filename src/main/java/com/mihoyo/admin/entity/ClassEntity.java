package com.mihoyo.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassEntity {

    private String id;
    private String institutionid;
    private String teacherId;
    private String name;
    private String currentGrade;
    private String inviteCode;
    private String status;
    private LocalDateTime createdAt;
}
