package com.yls.vocabverse.entity;

import lombok.Data;

@Data
public class Word {

    /**
     * 单词ID，主键，对应数据库的 BIGINT 类型
     */
    private Long id;


    /**
     * 词书 id，对应数据库的 BIGINT 类型
     */
    private Long wordbook_id;


    private String word;

    private String pronunciation;

    private String definition;

    private String exampleSentence;
}
