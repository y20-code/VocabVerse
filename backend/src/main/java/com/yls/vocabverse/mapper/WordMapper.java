package com.yls.vocabverse.mapper;

import com.yls.vocabverse.entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordMapper{
    @Select("select * from word where wordbook_id=#{wordbookId}")
    List<Word> findByWordbookId(Long wordbookId);
}
