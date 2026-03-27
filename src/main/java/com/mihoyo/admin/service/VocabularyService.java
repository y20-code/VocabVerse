package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.VocabularyCreateDTO;
import com.mihoyo.admin.dto.VocabularyDTO;

import java.util.List;

public interface VocabularyService {

    List<VocabularyDTO> getVocabulariesByBookId(String bookId);

    void createVocabulary(VocabularyCreateDTO dto);

    List<VocabularyDTO> getAllVocabularies();
}
