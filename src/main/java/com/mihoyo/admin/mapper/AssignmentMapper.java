package com.mihoyo.admin.mapper;


import com.mihoyo.admin.entity.AssignmentEntity;
import com.mihoyo.admin.entity.AssignmentProgressEntity;
import com.mihoyo.admin.entity.AssignmentWordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    int insertAssignment(AssignmentEntity assignment);

    int batchInsertAssignmentWords(@Param("list") List<AssignmentWordEntity> list);

    List<String> selectStudentIdsByClassId(@Param("classId") String classId);

    int batchInsertAssignmentProgress(@Param("list") List<AssignmentProgressEntity> list);

    List<String> selectClassWeakWordIds(@Param("classId") String classId);


}
