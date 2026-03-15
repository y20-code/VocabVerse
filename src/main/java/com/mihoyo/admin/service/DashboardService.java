package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.DashboardStatsDTO;

public interface DashboardService {

    DashboardStatsDTO getDashboardStats(String teacherId);

}
