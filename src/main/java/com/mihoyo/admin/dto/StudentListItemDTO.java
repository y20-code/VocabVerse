package com.mihoyo.admin.dto;


import lombok.Data;

@Data
public class StudentListItemDTO {

    private String key;         // 对应前端的 key (学生ID)
    private String name;        // 学生姓名
    private String avatar;      // 头像
    private String joinDate;    // 加入日期 (格式：yyyy-MM-dd)
    private String classId;     // 极其关键：所属班级ID (前端筛选用)
    private Integer words;      // 掌握词汇量
    private Integer stars;      // 熟练度星星
    private String lastActive;  // 最后活跃日期 (格式：yyyy-MM-dd)
}
