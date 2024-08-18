package com.example.market.dao;

import com.example.market.dto.FileDto;
import com.example.market.dto.ImageDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface ImageDao {
    @Insert("INSERT INTO image (fileId, imageData) VALUES (#{fileId}, #{imageData})")
    void insertImage(ImageDto imageDto) throws DataAccessException;

    @Select("SELECT * FROM file ORDER BY created_date DESC LIMIT 5")
    List<FileDto> findTop5ByOrderByCreatedDateDesc() throws DataAccessException;
}
