package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.VocabularyDTO;
import com.mihoyo.admin.mapper.VocabularyMapper;
import com.mihoyo.admin.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    @Autowired
    private VocabularyMapper vocabularyMapper;

    @Override
    public List<VocabularyDTO> getVocabulariesByBookId(String bookId) {
        return vocabularyMapper.selectVocabulariesByBookId(bookId);
    }
}
