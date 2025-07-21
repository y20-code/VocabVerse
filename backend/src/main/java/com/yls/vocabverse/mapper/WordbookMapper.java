package com.yls.vocabverse.mapper;


import com.yls.vocabverse.entity.Word;
import com.yls.vocabverse.entity.Wordbook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordbookMapper{

    @Select("select * from wordbook")
    List<Wordbook> getWordbooks();

    @Select("select wb.id,wb.name,wb.description,count(w.id) as word_count " +
            "from  wordbook wb left join  " +
            "word w on wb.id = w.wordbook_id" +
            " group by " +
            " wb.id,wb.name,wb.description")
    List<Wordbook> findAllWithWordCount();
}
