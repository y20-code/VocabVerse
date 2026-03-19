package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.StudentListItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<StudentListItemDTO> selectTeacherStudentsList(@Param("classIds") List<String> classIds);
}
