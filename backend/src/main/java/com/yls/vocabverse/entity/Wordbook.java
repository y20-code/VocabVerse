package com.yls.vocabverse.entity;

import lombok.Data;

@Data
public class Wordbook {

    private Long id;

    private String name;

    private String description;

    private int wordCount;

}
