package com.yls.vocabverse.service;

import com.yls.vocabverse.entity.Word;

import java.util.List;


public interface WordService {

    List<Word> getWordsByBookId(Long wordbookId);
}
