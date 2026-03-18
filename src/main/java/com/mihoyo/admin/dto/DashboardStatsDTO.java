package com.mihoyo.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "看板顶部统计数据传输对象")
public class DashboardStatsDTO {

    @Schema(description = "活跃班级数", example = "2")
    Integer activeClassCount;
    @Schema(description = "作业记录数", example = "2")
    Integer pendingAssignmentCount;
    @Schema(description = "平均正确率(%)", example = "75.0")
    Double avgCorrectRate;

    private List<HardWordDTO> hardWords;

}
