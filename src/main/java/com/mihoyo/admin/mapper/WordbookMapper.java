package com.mihoyo.admin.mapper;

import com.mihoyo.admin.dto.WordbookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordbookMapper {

    @Select("SELECT id, name FROM Wordbooks")
    List<WordbookDTO> selectAllWordbooks();
}
