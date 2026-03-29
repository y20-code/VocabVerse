package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class WordRecordDTO {

    private String assignmentId; // 当前作业 ID
    private String wordId;       // 单词 ID
    private Boolean isCorrect;   // 背对了还是背错了
}
