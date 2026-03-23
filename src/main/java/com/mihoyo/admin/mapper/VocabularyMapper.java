package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.VocabularyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VocabularyMapper {

    @Select("SELECT v.id, v.word, v.partOfSpeech, v.translation " +
            "FROM Vocabularies v " +
            "JOIN Wordbook_Vocabularies_Relation wvr ON v.id = wvr.wordId " +
            "WHERE wvr.bookId = #{bookId}")
    List<VocabularyDTO> selectVocabulariesByBookId(@Param("bookId") String bookId);
}
