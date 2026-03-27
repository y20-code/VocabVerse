package com.mihoyo.admin.dto;

import lombok.Data;

@Data
public class VocabularyCreateDTO {
    private String bookId;
    private String word;
    private String partOfSpeech;
    private String translation;
    private String example;
}
