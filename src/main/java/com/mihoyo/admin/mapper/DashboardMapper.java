package com.mihoyo.admin.mapper;



import com.mihoyo.admin.dto.HardWordDTO;
import com.mihoyo.admin.dto.SlackerDTO;
import com.mihoyo.admin.entity.AssignmentEntity;
import com.mihoyo.admin.entity.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    @Select("SELECT COUNT(*) FROM Classes WHERE teacherId=#{teacherId} AND status = 'active'")
    int countActiveClasses(@Param("teacherId") String teacherId);

    @Select("SELECT COUNT(*) FROM Assignments a " +
            "INNER JOIN Classes c ON a.classId = c.id " +
            "WHERE c.teacherId = #{teacherId}")
    int countAssignments(@Param("teacherId") String teacherId);


    Double avgStudentAccuracy(@Param("teacherId") String teacherId);

    @Select("SELECT * FROM Classes WHERE teacherId =#{teacherId} AND status = 'active' ")
    List<ClassEntity> selectClassesByTeacherId(@Param("teacherId") String teacherId);


    List<AssignmentEntity> selectYesterdayAssignments(
            @Param("classIds") List<String> classIds,
            @Param("targetDate") String targetDate
    );

    List<SlackerDTO> selectSlackersByAssignmentIds(
            @Param("assignmentIds") List<String> assignmentIds
    );

    List<Map<String, Object>> countStudentsByClassIds(@Param("classIds") List<String> classIds);

    //查询昨日作业学生最容易错的5个单词
    List<HardWordDTO> selectTopHardWords(@Param("classIds") List<String> classIds,@Param("targetDate") String targetDate);
}
