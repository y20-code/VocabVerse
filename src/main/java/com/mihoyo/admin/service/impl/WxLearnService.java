package com.mihoyo.admin.service.impl;


import com.mihoyo.admin.dto.WordRecordDTO;
import com.mihoyo.admin.mapper.WxLearnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WxLearnService {

    @Autowired
    private WxLearnMapper wxLearnMapper;

    public Map<String, Object> getLearningTask(String assignmentId, String studentId, String mode) {
        Map<String, Object> result = new HashMap<>();

        // 抓取目标单词
        List<Map<String, Object>> targetWords = wxLearnMapper.selectWordsByAssignment(assignmentId);

        // 为每个单词附加上该学生以前的“正确次数”（极其关键，决定了前端是出选择题还是拼写题）
        for (Map<String, Object> word : targetWords) {
            String wordId = String.valueOf(word.get("id"));
            Map<String, Object> record = wxLearnMapper.selectStudentWordRecord(studentId, wordId);
            word.put("correctCount", record != null ? record.get("correctCount") : 0);
        }

        // 抓取极其随意的干扰项（防全表扫描的神来之笔）
        List<String> distractors = wxLearnMapper.selectRandomDistractors();

        result.put("targetWords", targetWords);
        result.put("distractorOptions", distractors);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void recordWordStats(WordRecordDTO dto, String studentId) {
        int addCorrect = Boolean.TRUE.equals(dto.getIsCorrect()) ? 1 : 0;
        int addWrong = Boolean.FALSE.equals(dto.getIsCorrect()) ? 1 : 0;

        Map<String, Object> record = wxLearnMapper.selectStudentWordRecord(studentId, dto.getWordId());

        if (record != null) {
            // 以前背过，极其干脆地 UPDATE 加一
            String recordId = String.valueOf(record.get("id"));
            wxLearnMapper.updateWordRecord(recordId, addCorrect, addWrong);
        } else {
            // 第一次背，极其果断地 INSERT
            String newId = UUID.randomUUID().toString();
            wxLearnMapper.insertWordRecord(newId, studentId, dto.getWordId(), addCorrect, addWrong);
        }
    }
}
