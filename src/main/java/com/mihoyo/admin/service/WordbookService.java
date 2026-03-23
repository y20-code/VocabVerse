package com.mihoyo.admin.service;

import com.mihoyo.admin.dto.WordbookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordbookService {

    List<WordbookDTO> getAllWordbooks();
}
