package com.yls.vocabverse.controller;


import com.yls.vocabverse.dto.LearningRecordRequest;
import com.yls.vocabverse.service.LearningRecordService;
import com.yls.vocabverse.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "3. 学习记录模块", description = "记录用户的学习行为")
@RestController
@RequestMapping("/api/v1/learning-records")
public class LearningRecordController {

    @Autowired
    private LearningRecordService learningRecordService;

    @Operation(summary = "记录单词学习行为")
    @PostMapping
    public ResponseEntity<Result<?>> recordLearning(@RequestBody LearningRecordRequest request) {
        try {
            // Controller 的职责就是参数校验和转发，核心逻辑交给 Service
            learningRecordService.recordOrUpdate(request.getWordId(), request.getIsKnown());
            return ResponseEntity.ok(Result.success("学习记录已更新"));
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error(500, e.getMessage()));
        }

    }

}
