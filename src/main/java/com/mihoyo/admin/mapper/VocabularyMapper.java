package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.VocabularyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
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

    @Select("SELECT id FROM Vocabularies WHERE word = #{word} AND category = #{category} LIMIT 1")
    String selectIdByWordAndCategory(@Param("word") String word, @Param("category") String category);

    @Insert("INSERT INTO Vocabularies (id, word, partOfSpeech, translation, category) VALUES (#{id}, #{word}, #{pos}, #{trans}, #{category})")
    void insertVocabulary(@Param("id") String id, @Param("word") String word, @Param("pos") String pos, @Param("trans") String trans, @Param("category") String category);


    @Select("SELECT COUNT(*) FROM Wordbook_Vocabularies_Relation WHERE bookId = #{bookId} AND wordId = #{wordId}")
    int checkRelationExists(@Param("bookId") String bookId, @Param("wordId") String wordId);

    @Insert("INSERT INTO Wordbook_Vocabularies_Relation (id, bookId, wordId) VALUES (#{id}, #{bookId}, #{wordId})")
    void insertRelation(@Param("id") String id, @Param("bookId") String bookId, @Param("wordId") String wordId);

    @Insert("INSERT INTO Word_Examples (id, wordId, enSentence, cnTranslation, difficultyLevel) VALUES (#{id}, #{wordId}, #{en}, #{cn}, #{diff})")
    void insertExample(@Param("id") String id, @Param("wordId") String wordId, @Param("en") String en, @Param("cn") String cn, @Param("diff") int diff);

    @Select("SELECT id, word, partOfSpeech, translation, category FROM Vocabularies ORDER BY word ASC")
    List<VocabularyDTO> selectAllVocabularies();
}
