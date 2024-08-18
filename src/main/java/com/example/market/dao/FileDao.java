package com.example.market.dao;

import com.example.market.dto.FileDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface FileDao {

    @Insert("INSERT INTO file (name, description, imageUrl) VALUES (#{name}, #{description}, #{imageUrl})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFile(FileDto fileDto) throws DataAccessException;

    @Update("UPDATE file SET name = #{name}, description = #{description}, imageUrl = #{imageUrl} WHERE id = #{id}")
    void updateFile(FileDto fileDto) throws DataAccessException;

    @Select("SELECT * FROM file WHERE id = #{id}")
    FileDto getFileById(@Param("id") Long id) throws DataAccessException;
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "description", column = "description"),
//            @Result(property = "imageUrl", column = "imageUrl")
//    })

//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "description", column = "description"),
//            @Result(property = "imageUrl", column = "imageUrl")
//    })
    @Select("SELECT * FROM file")
    List<FileDto> getAllFiles();

}
