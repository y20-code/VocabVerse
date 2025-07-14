package com.yls.vocabverse.mapper;// LearningRecordMapper.java
import com.baomidou.mybatisplus.core.mapper.BaseMapper; // 假设使用 MyBatis-Plus
import com.yls.vocabverse.entity.LearningRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LearningRecordMapper extends BaseMapper<LearningRecord> {

    /**
     * 根据用户ID和单词ID查询学习记录
     * @param userId 用户ID
     * @param wordId 单词ID
     * @return 学习记录，如果不存在则返回 null
     */
    @Select("SELECT * FROM learning_record WHERE user_id = #{userId} AND word_id = #{wordId}")
    LearningRecord findByUserIdAndWordId(Long userId, Long wordId);
}