package com.yls.vocabverse.mapper;


import com.yls.vocabverse.entity.Wordbook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordbookMapper{

    @Select("select * from wordbook")
    List<Wordbook> getWordbooks();



}
