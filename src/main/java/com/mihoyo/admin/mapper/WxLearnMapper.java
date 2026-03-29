package com.mihoyo.admin.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WxLearnMapper {

    @Select("SELECT v.id, v.word, v.ukPhonetic AS phonetic, v.translation, v.partOfSpeech, " +
            "we.enSentence AS example " +
            "FROM Vocabularies v " +
            "JOIN Assignment_Words aw ON v.id = aw.wordId " +
            "LEFT JOIN Word_Examples we ON aw.exampleId = we.id " +
            "WHERE aw.assignmentId = #{assignmentId}")
    List<Map<String, Object>> selectWordsByAssignment(@Param("assignmentId") String assignmentId);


    @Select("SELECT translation FROM Vocabularies ORDER BY RAND() LIMIT 20")
    List<String> selectRandomDistractors();

    @Select("SELECT id, correctCount, incorrectCount AS wrongCount " +
            "FROM Student_Words WHERE studentId = #{studentId} AND wordId = #{wordId}")
    Map<String, Object> selectStudentWordRecord(@Param("studentId") String studentId, @Param("wordId") String wordId);

    @Insert("INSERT INTO Student_Words (id, studentId, wordId, correctCount, incorrectCount, lastReviewDate) " +
            "VALUES (#{id}, #{studentId}, #{wordId}, #{correctCount}, #{incorrectCount}, NOW())")
    void insertWordRecord(@Param("id") String id, @Param("studentId") String studentId,
                          @Param("wordId") String wordId,
                          @Param("correctCount") int correctCount, @Param("incorrectCount") int incorrectCount);

    @Update("UPDATE Student_Words SET correctCount = correctCount + #{addCorrect}, " +
            "incorrectCount = incorrectCount + #{addWrong}, lastReviewDate = NOW() WHERE id = #{recordId}")
    void updateWordRecord(@Param("recordId") String recordId, @Param("addCorrect") int addCorrect, @Param("addWrong") int addWrong);

    @Select("SELECT word FROM Vocabularies WHERE id = #{wordId}")
    String selectWordStringById(@Param("wordId") String wordId);
}
