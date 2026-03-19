package com.mihoyo.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateAssignmentReqDTO {

    private List<String> classIds;

    private String title;               // 作业标题前缀 (可选，后端也可以自动生成)
    private String targetGrade;         // 目标词库名称 (如 "高一核心词汇")
    private LocalDateTime deadline;     // 截止时间

    // 老师选中的基础新词 ID 列表 (全班统一的)
    private List<String> baseWordIds;

    // 极其核心的开关：是否开启千人千面！
    private Boolean isPersonalized;
}
