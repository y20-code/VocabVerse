package com.yls.vocabverse.service.impl;

import com.yls.vocabverse.entity.Word;
import com.yls.vocabverse.entity.Wordbook;
import com.yls.vocabverse.mapper.WordbookMapper;
import com.yls.vocabverse.service.WordbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordbookServiceImpl implements WordbookService {

    @Autowired
    private WordbookMapper wordbookMapper;

    @Override
    public List<Wordbook> getWordbooks() {
        return wordbookMapper.getWordbooks();
    }


}
