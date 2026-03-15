package com.mihoyo.admin.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashboardMapper {

    @Select("SELECT COUNT(*) FROM Classes WHERE teacherId=#{teacherId} AND status = 'active'")
    int countActiveClasses(@Param("teacherId") String teacherId);

    @Select("SELECT COUNT(*) FROM Assignments a " +
            "INNER JOIN Classes c ON a.classId = c.id " +
            "WHERE c.teacherId = #{teacherId}")
    int countAssignments(@Param("teacherId") String teacherId);

    @Select("SELECT AVG(l.isCorrect) * 100 FROM Learning_Logs l " +
            "INNER JOIN Users u ON l.studentId = u.id " +
            "INNER JOIN Classes c ON u.classId = c.id " +
            "WHERE c.teacherId = #{teacherId}")
    Double avgStudentAccuracy(@Param("teacherId") String teacherId);

}
