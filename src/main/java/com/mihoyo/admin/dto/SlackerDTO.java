package com.mihoyo.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "未完成作业名单")
public class SlackerDTO {

    @JsonIgnore
    private String classId;

    @Schema(description = "学生ID")
    private String id;

    @Schema(description = "学生姓名")
    private String name;

    @Schema(description = "作业完成进度(0-100)")
    private Integer progress;
}
