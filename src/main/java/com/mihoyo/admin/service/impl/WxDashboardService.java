package com.mihoyo.admin.service.impl;


import com.mihoyo.admin.dto.WxDashboardHomeDTO;
import com.mihoyo.admin.mapper.WxDashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class WxDashboardService {

    @Autowired
    private WxDashboardMapper wxDashboardMapper;

    public WxDashboardHomeDTO getHomeData(String studentId) {

        WxDashboardHomeDTO dto = new WxDashboardHomeDTO();
        // 初始值兜底
        dto.setTotalWords(0);
        dto.setLearnedWords(0);
        dto.setStreakDays(3); // MVP阶段先给个鼓励值
        dto.setPoints(150);   // MVP阶段先给个鼓励值

        // 1. 查班级
        String classId = wxDashboardMapper.selectClassIdByStudentId(studentId);
        if (classId == null) {
            dto.setTaskTitle("您尚未被分配到任何班级 📭");
            return dto;
        }

        // 2. 查作业
        LocalDate today = LocalDate.now();
        LocalDate startDate;

        if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
            startDate = today.minusDays(3); // 周一往前推3天，起点是周五
        } else if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            startDate = today.minusDays(2); // 周日往前推2天，起点是周五
        } else if (today.getDayOfWeek() == DayOfWeek.SATURDAY) {
            startDate = today.minusDays(1); // 周六往前推1天，起点是周五
        } else {
            startDate = today.minusDays(1); // 平时工作日，起点就是昨天
        }

        // 把算好的时间，格式化成 MySQL 认识的 "YYYY-MM-DD" 字符串
        String startDateStr = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // ===================================================================

        // 2. 拿着算好的极其精准的日期，去查那天的作业
        Map<String, String> targetTask = wxDashboardMapper.selectLatestAssignmentInWindow(classId, startDateStr);

        if (targetTask == null || targetTask.isEmpty()) {
            dto.setTaskTitle("今日暂无任务（自 " + startDateStr + " 起未布置）📭");
            return dto;
        }

        String assignmentId = targetTask.get("id");
        dto.setAssignmentId(assignmentId);
        dto.setTaskTitle(targetTask.get("title"));

        // 3. 查总词数
        Integer totalWords = wxDashboardMapper.countWordsInAssignment(assignmentId);
        dto.setTotalWords(totalWords != null ? totalWords : 0);

        // 4. 查学生学习进度（算出已学词数）
        Integer progress = wxDashboardMapper.selectStudentProgress(assignmentId, studentId);
        if (progress != null && totalWords != null) {
            // 进度是百分比（如 20），算一下具体背了多少词
            int learned = (int) Math.round(totalWords * (progress / 100.0));
            dto.setLearnedWords(learned);
        }

        return dto;
    }
}
