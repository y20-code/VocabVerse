package com.yls.vocabverse.service.impl;

import com.yls.vocabverse.entity.Word;
import com.yls.vocabverse.mapper.WordMapper;
import com.yls.vocabverse.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Word> getWordsByBookId(Long wordbookId) {

        return wordMapper.findByWordbookId(wordbookId);
    }
}
