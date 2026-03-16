package com.mihoyo.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "班级昨日作业完成情况")
public class ClassProgressDTO {

    @Schema(description = "班级名称")
    private String name;

    @Schema(description = "完成率")
    private Integer value;

    @Schema(description = "昨日是否有作业")
    private Boolean hasAssignment;

    @Schema(description = "统计日期")
    private String statDate;

    @Schema(description = "未通关学生名单")
    private List<SlackerDTO> slackers;

}
