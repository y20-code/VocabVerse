package com.yls.vocabverse.service.impl;

import com.yls.vocabverse.entity.LearningRecord;


import com.yls.vocabverse.entity.User;
import com.yls.vocabverse.mapper.LearningRecordMapper;
import com.yls.vocabverse.mapper.UserMapper;
import com.yls.vocabverse.service.LearningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
public class LearningRecordServiceImpl implements LearningRecordService {

    @Autowired
    private LearningRecordMapper learningRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void recordOrUpdate(Long wordId, Boolean isKnown) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("用户未登录或认证失败");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();


        User currentUser = userMapper.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("无法找到当前登录用户的信息");
        }
        Long userId = currentUser.getId();

        // --- 【关键步骤二：判断记录是否存在，并执行不同逻辑】 ---
        LearningRecord existingRecord = learningRecordMapper.findByUserIdAndWordId(userId, wordId);

        if (existingRecord != null) {
            // --- 记录已存在，执行更新逻辑 ---
            existingRecord.setIsKnown(isKnown);
            existingRecord.setLastReviewTime(LocalDateTime.now());
            // 复习次数加一
            existingRecord.setReviewCount(existingRecord.getReviewCount() + 1);

            learningRecordMapper.updateById(existingRecord);
            System.out.println("更新学习记录: " + existingRecord);

        } else {
            // --- 记录不存在，执行插入新纪录的逻辑 ---
            LearningRecord newRecord = new LearningRecord();
            newRecord.setUserId(userId);
            newRecord.setWordId(wordId);
            newRecord.setIsKnown(isKnown);
            newRecord.setLastReviewTime(LocalDateTime.now());
//            newRecord.setReviewCount(1); // 第一次学习，复习次数为1

            learningRecordMapper.insert(newRecord); // 使用 MyBatis-Plus 的插入方法
            System.out.println("创建新学习记录: " + newRecord);
        }
    }
}
