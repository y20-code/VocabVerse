package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class WxDashboardHomeDTO {
    private String taskTitle;      // 任务标题
    private String assignmentId;   // 任务ID
    private Integer totalWords;    // 总词数
    private Integer learnedWords;  // 已学词数
    private Integer streakDays;    // 连续打卡天数（MVP先写死）
    private Integer points;        // 积分（MVP先写死）
}
