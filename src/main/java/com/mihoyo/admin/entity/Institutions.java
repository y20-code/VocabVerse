package com.mihoyo.admin.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Institutions {
    private String id;
    private String name;
    private String licenseType;
    private LocalDateTime expireDate;
}
