package com.mihoyo.admin.controller;


import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.VocabularyDTO;
import com.mihoyo.admin.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
