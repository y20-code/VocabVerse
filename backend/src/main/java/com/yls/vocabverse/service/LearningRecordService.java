package com.yls.vocabverse.service;

public interface LearningRecordService {
    void recordOrUpdate(Long wordId, Boolean isKnown);
}