package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.DashboardStatsDTO;
import com.mihoyo.admin.mapper.DashboardMapper;
import com.mihoyo.admin.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public DashboardStatsDTO getDashboardStats(String teacherId) {

        Integer activeClasses = dashboardMapper.countActiveClasses(teacherId);

        Integer assignments = dashboardMapper.countAssignments(teacherId);

        Double avgStudentAccuracy = dashboardMapper.avgStudentAccuracy(teacherId);

        if(avgStudentAccuracy == null){
            avgStudentAccuracy = 0.0;
        }


        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();

        dashboardStatsDTO.setActiveClassCount(activeClasses);
        dashboardStatsDTO.setPendingAssignmentCount(assignments);
        dashboardStatsDTO.setAvgCorrectRate(avgStudentAccuracy);

        return dashboardStatsDTO;
    }
}
