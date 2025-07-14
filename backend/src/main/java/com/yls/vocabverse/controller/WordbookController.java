package com.yls.vocabverse.controller;

import com.yls.vocabverse.entity.Word;
import com.yls.vocabverse.entity.Wordbook;
import com.yls.vocabverse.service.WordService;
import com.yls.vocabverse.service.WordbookService;
import com.yls.vocabverse.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 词书与单词相关 API 的控制器
 * @author 杨林森
 */
@Tag(name = "2.词书与单词模块",description = "提供词书查询、单词获取等核心学习接口")
@RestController
@RequestMapping("/api/v1/wordbooks")
public class WordbookController {

    @Autowired
    private WordbookService wordbookService;

    @Autowired
    private WordService wordService;

    @Operation(summary = "获取所有单词书列表", description = "查询系统中所有公开的词书，用于学生选择学习内容。")
    @GetMapping
    public ResponseEntity<Result<?>> getWordbooks() {

        List<Wordbook> wordbookList = wordbookService.getWordbooks();

        return ResponseEntity.ok(Result.success(wordbookList));
    }

    @Operation(summary = "根据词书ID获取单词列表")
    @GetMapping("/{wordbookId}/words")
    public ResponseEntity<Result<?>> getWord(@Parameter(description = "词书的ID", required = true) @PathVariable Long wordbookId) {

        List<Word> wordList = wordService.getWordsByBookId(wordbookId);

        return  ResponseEntity.ok(Result.success(wordList));
    }

}
