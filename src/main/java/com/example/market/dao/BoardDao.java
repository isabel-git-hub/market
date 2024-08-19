package com.example.market.dao;

import com.example.market.dto.BoardDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface BoardDao {

    // 페이징 처리된 게시판 목록 조회
    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount, board_type AS boardType " +
            "FROM board WHERE board_type = #{boardType} LIMIT #{offset}, #{size}")
    List<BoardDto> findBoardsByTypeWithPaging(@Param("boardType") String boardType,
                                              @Param("offset") int offset,
                                              @Param("size") int size) throws DataAccessException;
    // 게시판 개수 조회
    @Select("SELECT COUNT(*) FROM board WHERE board_type = #{boardType}")
    int countBoardsByType(@Param("boardType") String boardType) throws DataAccessException;

    // 게시판 조회
    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount, board_type AS boardType " +
            "FROM board WHERE id = #{id}")
    BoardDto findBoardById(@Param("id") Long id) throws DataAccessException;

    // 게시판 추가
    @Insert("INSERT INTO board (title, description, author, created_date, updated_date, hit_count, board_type) " +
            "VALUES (#{title}, #{description}, #{author}, NOW(), NOW(), #{hitCount}, #{boardType})")
    void insertBoard(BoardDto boardDto) throws DataAccessException;

    // 게시판 수정
    @Update("UPDATE board SET title = #{title}, description = #{description}, author = #{author}, updated_date = NOW(), " +
            "hit_count = #{hitCount} WHERE id = #{id}")
    void updateBoard(BoardDto boardDto) throws DataAccessException;

    // 조회수 증가
    @Update("UPDATE board SET hit_count = hit_count + 1 WHERE id = #{id}")
    void incrementHitCount(@Param("id") Long id) throws DataAccessException;

    // 게시판 삭제
    @Delete("DELETE FROM board WHERE id = #{id}")
    void deleteBoard(@Param("id") Long id) throws DataAccessException;

    @Select("SELECT id, title, description, author, created_date AS createdDate, updated_date AS updatedDate, hit_count AS hitCount, board_type AS boardType " +
            "FROM board WHERE board_type = #{boardType}")
    List<BoardDto> findAllBoardsByType(@Param("boardType") String boardType) throws DataAccessException;
}
