package com.mihoyo.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentListResponseDTO {
    private Integer totalStudents;              // 学生总数
    private Integer avgProgress;                // 平均进度 (咱们先保留这个坑位)
    private List<SimpleClassDTO> classes;       // 老师的班级列表 (前端下拉框用)
    private List<StudentListItemDTO> students;  // 学生大名单
}
