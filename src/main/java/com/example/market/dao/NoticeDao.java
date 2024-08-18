package com.example.market.dao;

import com.example.market.dto.NoticeDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface NoticeDao {

    @Select("SELECT id, title, content, author, created_date AS createdDate, updated_date AS updatedDate, hit_cnt AS hitCount " +
            "FROM notice ORDER BY created_date DESC")
    List<NoticeDto> selectAllNotices() throws DataAccessException;

    @Select("SELECT id, title, content, author, created_date AS createdDate, updated_date AS updatedDate, hit_cnt AS hitCount " +
            "FROM notice WHERE id = #{id}")
    NoticeDto selectNoticeById(@Param("id") Long id) throws DataAccessException;

    @Insert("INSERT INTO notice (title, content, author, created_date, updated_date, hit_cnt) " +
            "VALUES (#{title}, #{content}, #{author}, NOW(), NOW(), #{hitCount})")
    void insertNotice(NoticeDto noticeDto) throws DataAccessException;

    @Update("UPDATE notice SET title = #{title}, content = #{content}, author = #{author}, updated_date = NOW(), hit_cnt = #{hitCount} " +
            "WHERE id = #{id}")
    void updateNotice(NoticeDto noticeDto) throws DataAccessException;

    @Delete("DELETE FROM notice WHERE id = #{id}")
    void deleteNotice(@Param("id") Long id) throws DataAccessException;

    @Update("UPDATE notice SET hit_cnt = hit_cnt + 1 WHERE id = #{id}")
    void incrementHitCount(@Param("id") Long id) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM notice")
    int countAllNotices() throws DataAccessException;

    @Select("SELECT id, title, content, author, created_date AS createdDate, updated_date AS updatedDate, hit_cnt AS hitCount " +
            "FROM notice ORDER BY created_date DESC LIMIT #{offset}, #{size}")
    List<NoticeDto> findNoticesWithPaging(@Param("offset") int offset, @Param("size") int size) throws DataAccessException;
}
