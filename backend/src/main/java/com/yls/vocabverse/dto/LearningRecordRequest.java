package com.yls.vocabverse.dto;

import lombok.Data;

@Data
public class LearningRecordRequest {
    private Long wordId;
    private Boolean isKnown;
}