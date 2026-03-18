package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.ClassProgressDTO;
import com.mihoyo.admin.dto.DashboardStatsDTO;
import com.mihoyo.admin.dto.HardWordDTO;
import com.mihoyo.admin.dto.SlackerDTO;
import com.mihoyo.admin.entity.AssignmentEntity;
import com.mihoyo.admin.entity.ClassEntity;
import com.mihoyo.admin.mapper.DashboardMapper;
import com.mihoyo.admin.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public DashboardStatsDTO getDashboardStats(String teacherId) {

        //获取班级
        Integer activeClasses = dashboardMapper.countActiveClasses(teacherId);

        //获取作业记录
        Integer assignments = dashboardMapper.countAssignments(teacherId);

        //获取平均正确率
        Double avgStudentAccuracy = dashboardMapper.avgStudentAccuracy(teacherId);

        if(avgStudentAccuracy == null){
            avgStudentAccuracy = 0.0;
        }

        List<HardWordDTO> hardWords = new ArrayList<>();
        
        List<ClassEntity> myClasses = dashboardMapper.selectClassesByTeacherId(teacherId);
        
        if(myClasses != null && !myClasses.isEmpty()){
            List<String> classIds = myClasses.stream()
                    .map(ClassEntity::getId)
                    .collect(Collectors.toList());

            LocalDate today = LocalDate.now();
            LocalDate targetDate = today.getDayOfWeek() == DayOfWeek.MONDAY ? today.minusDays(3) : today.minusDays(1);
            String statDateStr = targetDate.toString();

            hardWords = dashboardMapper.selectTopHardWords(classIds,statDateStr);
        }

        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();

        dashboardStatsDTO.setActiveClassCount(activeClasses);
        dashboardStatsDTO.setPendingAssignmentCount(assignments);
        dashboardStatsDTO.setAvgCorrectRate(avgStudentAccuracy);
        dashboardStatsDTO.setHardWords(hardWords);

        return dashboardStatsDTO;
    }

    @Override
    public List<ClassProgressDTO> getClassProgress(String teacherId) {

        //查出老师管理的班级
        List<ClassEntity> myClasses = dashboardMapper.selectClassesByTeacherId(teacherId);

        if(myClasses == null || myClasses.isEmpty()){
            return new ArrayList<>();
        }

        List<String> classIds = myClasses.stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toList());

        //算日子
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.getDayOfWeek() == DayOfWeek.MONDAY ? today.minusDays(3) : today.minusDays(1);
        String statDateStr = targetDate.toString();

        //查询昨天布置的作业
        List<AssignmentEntity> assignmentEntities = dashboardMapper.selectYesterdayAssignments(classIds, statDateStr);

        List<String> assignmentIds = assignmentEntities.stream()
                .map(AssignmentEntity::getId)
                .collect(Collectors.toList());

        //查询每个班的总人数
        List<Map<String, Object>> studentsCounts = dashboardMapper.countStudentsByClassIds(classIds);

        Map<String,Integer> studentsCountMap = studentsCounts.stream()
                .collect(Collectors.toMap(
                        map -> String.valueOf(map.get("classId")),
                        map -> ((Number) map.get("total")).intValue()
                ));


        //查询未做作业学生名单
        List<SlackerDTO> allSlackers = new ArrayList<>();
        if(!assignmentIds.isEmpty()){
            allSlackers = dashboardMapper.selectSlackersByAssignmentIds(assignmentIds);
        }

        Map<String,List<SlackerDTO>> slackersByClass = allSlackers.stream()
                .collect(Collectors.groupingBy(SlackerDTO::getClassId));

        List<ClassProgressDTO> result = new ArrayList<>();


        for(ClassEntity cls : myClasses){
            ClassProgressDTO dto = new ClassProgressDTO();

            dto.setName(cls.getName());
            dto.setStatDate(statDateStr);

            //判断昨天是否有作业
            boolean hasAssignment = assignmentEntities.stream()
                    .anyMatch(assignmentEntity -> assignmentEntity.getClassId().equals(cls.getId()));

            dto.setHasAssignment(hasAssignment);

            if(hasAssignment){
                List<SlackerDTO> clsSlackers = slackersByClass.getOrDefault(cls.getId(),new ArrayList<>());
                dto.setSlackers(clsSlackers);

                //完成率
                int total = studentsCountMap.getOrDefault(cls.getId(), 0);
                int slackerCount = clsSlackers.size();

                if (total > 0) {
                    Integer value = (total - slackerCount) * 100 / total;
                    dto.setValue(value);
                } else {
                    dto.setValue(0);
                }
            }else{
                dto.setSlackers(new ArrayList<>());
                dto.setValue(0);
            }

            result.add(dto);
        }


        return result;

    }
}
