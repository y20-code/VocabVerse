package com.mihoyo.admin.controller;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.VocabularyCreateDTO;
import com.mihoyo.admin.dto.VocabularyDTO;
import com.mihoyo.admin.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vocabularies")
public class VocabularyController {

    @Autowired
    private VocabularyService vocabularyService;


    @GetMapping
    public Result<List<VocabularyDTO>> getVocabulariesByBookId(@RequestParam("bookId") String bookId) {
        List<VocabularyDTO> list = vocabularyService.getVocabulariesByBookId(bookId);
        return Result.success(list);
    }

    @PostMapping
    public Result<Void> createVocabulary(@RequestBody VocabularyCreateDTO dto) {
        vocabularyService.createVocabulary(dto);
        return Result.success(); // 极其丝滑地返回成功信号
    }

    @GetMapping("/all")
    public Result<List<VocabularyDTO>> getAllVocabularies() {
        List<VocabularyDTO> list = vocabularyService.getAllVocabularies();
        return Result.success(list);
    }
}
