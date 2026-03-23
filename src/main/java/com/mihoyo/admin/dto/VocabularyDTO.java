package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class VocabularyDTO {

    private String id;
    private String word;
    private String partOfSpeech; // 词性 (例如: n., v.)
    private String translation;  // 中文翻译
}
