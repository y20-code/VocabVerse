package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.VocabularyCreateDTO;
import com.mihoyo.admin.dto.VocabularyDTO;
import com.mihoyo.admin.mapper.VocabularyMapper;
import com.mihoyo.admin.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    @Autowired
    private VocabularyMapper vocabularyMapper;

    @Override
    public List<VocabularyDTO> getVocabulariesByBookId(String bookId) {
        return vocabularyMapper.selectVocabulariesByBookId(bookId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createVocabulary(VocabularyCreateDTO dto) {

        String category = dto.getBookId();

        String wordId = vocabularyMapper.selectIdByWordAndCategory(dto.getWord(),category);

        if (wordId == null) {
            wordId = UUID.randomUUID().toString(); // 生成 36 位全球唯一 ID
            vocabularyMapper.insertVocabulary(wordId, dto.getWord(), dto.getPartOfSpeech(), dto.getTranslation(),category);
        }

        if (vocabularyMapper.checkRelationExists(dto.getBookId(), wordId) == 0) {
            String relId = UUID.randomUUID().toString();
            vocabularyMapper.insertRelation(relId, dto.getBookId(), wordId);
        }

        if (dto.getExample() != null && !dto.getExample().trim().isEmpty()) {
            String exampleId = UUID.randomUUID().toString();
            // 因为前端暂时没传例句的中文，咱们默认填个空字符串，难度默认给 1
            vocabularyMapper.insertExample(exampleId, wordId, dto.getExample(), "", 1);
        }
    }

    public List<VocabularyDTO> getAllVocabularies() {
        return vocabularyMapper.selectAllVocabularies();
    }
}
