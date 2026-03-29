package com.mihoyo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface WxDashboardMapper {

    // 查询学生所在的班级
    @Select("SELECT classId FROM Users WHERE id = #{studentId}")
    String selectClassIdByStudentId(@Param("studentId") String studentId);

    // 查询这个班级的作业
    @Select("SELECT id, title FROM Assignments WHERE classId = #{classId} AND DATE(createdAt) >= #{startDate} ORDER BY createdAt DESC LIMIT 1")
    Map<String, String> selectLatestAssignmentInWindow(@Param("classId") String classId, @Param("startDate") String startDate);

    // 查询这个作业一共包含多少个单词
    @Select("SELECT COUNT(*) FROM Assignment_Words WHERE assignmentId = #{assignmentId}")
    Integer countWordsInAssignment(@Param("assignmentId") String assignmentId);

    // 查询这个学生作业进度
    @Select("SELECT progress FROM Assignment_Progress WHERE assignmentId = #{assignmentId} AND studentId = #{studentId} LIMIT 1")
    Integer selectStudentProgress(@Param("assignmentId") String assignmentId, @Param("studentId") String studentId);
}
