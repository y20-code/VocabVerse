package com.mihoyo.admin.controller;

import com.mihoyo.admin.common.Result;
import com.mihoyo.admin.dto.WordbookDTO;
import com.mihoyo.admin.service.WordbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/wordbooks")
public class WordbookController {

    @Autowired
    private WordbookService wordbookService;

    @GetMapping
    public Result<List<WordbookDTO>> getAllWordbooks() {
        List<WordbookDTO> list = wordbookService.getAllWordbooks();
        return Result.success(list); // 极其丝滑地打包返回
    }
}
