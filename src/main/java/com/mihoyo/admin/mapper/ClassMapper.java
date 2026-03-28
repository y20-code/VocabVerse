package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.ClassDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("SELECT id, name FROM Classes WHERE teacherId = #{teacherId} AND status = 'active'")
    List<ClassDTO> selectClassesByTeacherId(@Param("teacherId") String teacherId);

    @Insert("INSERT INTO Classes (id, teacherId, name, currentGrade, inviteCode, status, createdAt) " +
            "VALUES (#{id}, #{teacherId}, #{name}, #{grade}, #{inviteCode}, 'active', #{createdAt})")
    void insertClass(@Param("id") String id, @Param("teacherId") String teacherId,
                     @Param("name") String name, @Param("grade") String grade,
                     @Param("inviteCode") String inviteCode, @Param("createdAt") Date createdAt);
}
