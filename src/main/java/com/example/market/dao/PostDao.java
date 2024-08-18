package com.example.market.dao;

import com.example.market.dto.PostDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface PostDao {
    @Select("SELECT * FROM post ORDER BY created_date DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "author", column = "author"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "hitCount", column = "hit_count")
    })
    List<PostDto> selectAllPosts() throws DataAccessException;

    @Select("SELECT * FROM post WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "author", column = "author"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "hitCount", column = "hit_count")
    })
    PostDto selectPostById(@Param("id") Long id) throws DataAccessException;

    @Insert("INSERT INTO post (title, content, author, created_date, updated_date, hit_count) " +
            "VALUES (#{title}, #{content}, #{author}, NOW(), NOW(), 0)")
    void insertPost(PostDto postDto) throws DataAccessException;

    @Update("UPDATE post SET title = #{title}, content = #{content}, updated_date = NOW(), hit_count = #{hitCount} " +
            "WHERE id = #{id} AND author = #{author}")
    void updatePost(PostDto postDto) throws DataAccessException;

    @Delete("DELETE FROM post WHERE id = #{id}")
    void deletePost(@Param("id") Long id) throws DataAccessException;

    @Update("UPDATE post SET hit_count = hit_count + 1 WHERE id = #{id}")
    void incrementHitCount(@Param("id") Long id) throws DataAccessException;

    @Select("SELECT * FROM post WHERE title LIKE CONCAT('%', #{keyword}, '%') ORDER BY created_date DESC LIMIT #{offset}, #{recordSize}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "author", column = "author"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "hitCount", column = "hit_count")
    })
    List<PostDto> searchPosts(@Param("keyword") String keyword,
                              @Param("recordSize") int recordSize,
                              @Param("offset") int offset) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM post WHERE title LIKE CONCAT('%', #{keyword}, '%')")
    int countPosts(@Param("keyword") String keyword) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM post WHERE title LIKE CONCAT('%', #{keyword}, '%')")
    int getTotalPostCountByKeyword(@Param("keyword") String keyword) throws DataAccessException;
}
