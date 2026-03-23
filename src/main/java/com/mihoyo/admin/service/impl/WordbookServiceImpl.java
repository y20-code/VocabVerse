package com.mihoyo.admin.service.impl;

import com.mihoyo.admin.dto.WordbookDTO;
import com.mihoyo.admin.mapper.WordbookMapper;
import com.mihoyo.admin.service.WordbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordbookServiceImpl implements WordbookService {

    @Autowired
    private WordbookMapper wordbookMapper;

    public List<WordbookDTO> getAllWordbooks() {
        return wordbookMapper.selectAllWordbooks();
    }
}
