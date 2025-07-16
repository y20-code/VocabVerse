package com.yls.vocabverse.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class LearningRecord {

    private Long id;

    private Long userId;

    private Long wordId;

    private Boolean isKnown;

    private Integer reviewCount;

    private LocalDateTime lastReviewTime;

}
