package com.example.market.service;

import com.example.market.dto.MemberDto;

import java.util.List;

public interface MemberService {
    boolean checkLogin(String userid, String userpw);
    boolean checkMember(MemberDto memberDto);
    boolean putMember(MemberDto memberDto);
    boolean checkId(String userid);
//    boolean isAdmin(String userid);
    MemberDto findByUserid(String userid);
    MemberDto getMemberInfo(String userid);
    MemberDto editMemberInfo(MemberDto memberDto);
    void unregistUser(String userid);
    List<MemberDto> getMemberList();
    void editUser(MemberDto dto);
    void deleteUser(String userid);

    String getMemberByName(MemberDto dto);
}
