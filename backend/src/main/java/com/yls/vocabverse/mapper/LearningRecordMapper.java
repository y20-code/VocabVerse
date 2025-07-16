package com.yls.vocabverse.mapper;// LearningRecordMapper.java
import com.yls.vocabverse.entity.LearningRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LearningRecordMapper{

    /**
     * 根据用户ID和单词ID查询学习记录
     * @param userId 用户ID
     * @param wordId 单词ID
     * @return 学习记录，如果不存在则返回 null
     */
    @Select("SELECT * FROM learning_record " +
            "WHERE user_id = #{userId} AND word_id = #{wordId}")
    LearningRecord findByUserIdAndWordId(Long userId, Long wordId);

    /**
     * 根据 ID 更新一条已存在的学习记录
     * @param existingRecord 包含 ID 和待更新数据的 LearningRecord 对象
     */
    @Update("UPDATE learning_record " +
            "SET " +
            "  is_known = #{isKnown}, " +
            "  review_count = #{reviewCount}, " +
            "  last_review_time = #{lastReviewTime} " +
            "WHERE id = #{id}")
    void updateById(LearningRecord existingRecord);

    /**
     * 插入一条新的学习记录
     * @param newRecord 包含待插入数据的 LearningRecord 对象
     */
    @Insert("INSERT INTO learning_record (user_id, word_id, is_known, last_review_time) " +
            "VALUES (#{userId}, #{wordId}, #{isKnown}, #{lastReviewTime})")
    void insert(LearningRecord newRecord);
}