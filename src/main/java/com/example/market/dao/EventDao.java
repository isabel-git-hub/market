package com.example.market.dao;

import com.example.market.dto.EventDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface EventDao {

    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount " +
            "FROM event ORDER BY created_date DESC")
    List<EventDto> findAll() throws DataAccessException;

    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount " +
            "FROM event WHERE id = #{id}")
    EventDto findById(@Param("id") Long id) throws DataAccessException;

    @Insert("INSERT INTO event (title, description, author, created_date, updated_date, hit_count) " +
            "VALUES (#{title}, #{description}, #{author}, NOW(), NOW(), #{hitCount})")
    void insert(EventDto event) throws DataAccessException;

    @Update("UPDATE event SET title = #{title}, description = #{description}, author = #{author}, updated_date = NOW(), hit_count = #{hitCount} " +
            "WHERE id = #{id}")
    void update(EventDto event) throws DataAccessException;

    @Delete("DELETE FROM event WHERE id = #{id}")
    void delete(@Param("id") Long id) throws DataAccessException;

    @Update("UPDATE event SET hit_count = hit_count + 1 WHERE id = #{id}")
    void incrementHitCount(@Param("id") Long id) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM event")
    int countAllEvents() throws DataAccessException;

    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount " +
            "FROM event ORDER BY created_date DESC LIMIT #{offset}, #{recordSize}")
    List<EventDto> findEventsWithPaging(@Param("offset") int offset, @Param("recordSize") int recordSize) throws DataAccessException;
}
