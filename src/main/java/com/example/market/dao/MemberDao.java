package com.example.market.dao;

import com.example.market.dto.MemberDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface MemberDao {

    @Select("SELECT COUNT(*) FROM member WHERE userid=#{userid} AND birthdate=#{birthdate} AND telnumber=#{telnumber}")
    boolean checkMember(MemberDto dto) throws DataAccessException;

    @Insert("INSERT INTO member (userid, userpw, username, birthdate, gender, telnumber, addr, email, permit) " +
            "VALUES (#{userid}, #{userpw}, #{username}, #{birthdate}, #{gender}, #{telnumber}, #{addr}, #{email}, #{permit})")
    boolean insertMember(MemberDto memberDto) throws DataAccessException;

    @Select("select count(*) from member where userid=#{userid}")
    int checkId(String userid) throws DataAccessException;

    @Select("select * from member where userid=#{userid}")
    MemberDto findByUserid(@Param("userid") String userid) throws DataAccessException;

    @Update("update member set userpw=#{userpw} where userid=#{userid}")
    void updateUserpw(@Param("userid") String userid, @Param("userpw") String userpw) throws DataAccessException;

    @Update("update member set username=#{username} where userid=#{userid}")
    void updateUsername(@Param("userid") String userid, @Param("username") String username) throws DataAccessException;

    @Update("update member set telnumber=#{telnumber} where userid=#{userid}")
    void updateTelnumber(@Param("userid") String userid, @Param("telnumber") String telnumber) throws DataAccessException;

    @Update("update member set addr=#{addr}  where userid=#{userid}")
    void updateAddr(@Param("userid") String userid, @Param("addr") String addr) throws DataAccessException;

    @Delete("delete from member where userid=#{userid}")
    void deleteUser(@Param("userid") String userid) throws DataAccessException;

    @Select("select * from member")
    List<MemberDto> selectMember() throws DataAccessException;

    @Update("update member set permit=#{permit}  where userid=#{userid}")
    void updatePermit(@Param("userid") String userid, @Param("permit") int permit) throws DataAccessException;

    @Select("select userid from member where username=#{username} and birthdate=#{birthdate} and telnumber=#{telnumber}")
    String selectUserId(MemberDto dto) throws DataAccessException;
}
