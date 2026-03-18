package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.ClassProgressDTO;
import com.mihoyo.admin.dto.DashboardStatsDTO;

import java.util.List;

public interface DashboardService {

    DashboardStatsDTO getDashboardStats(String teacherId);

    List<ClassProgressDTO> getClassProgress(String teacherId);

}
