package com.example.market.service;

import com.example.market.dao.MemberDao;
import com.example.market.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkLogin(String userid, String userpw) {
       MemberDto memberDto = memberDao.findByUserid(userid);
       return memberDto != null && memberDto.getUserpw().equals(userpw);
   }

   @Override
   public boolean checkMember(MemberDto dto) {
       return memberDao.checkMember(dto);
   }
   @Override
    public boolean putMember(MemberDto memberDto) {
       // 기본 역할 설정 (일반 사용자)
//       memberDto.setRole("USER");
       return memberDao.insertMember(memberDto);
   }
   @Override
    public boolean checkId(String userid) {
       return memberDao.checkId(userid) == 0;
   }

//   @Override
//    public boolean isAdmin(String userid) {
//       MemberDto memberDto = memberDao.findByUserid(userid);
//       return memberDto != null && "ADMIN".equals(memberDto.getRole());
//   }

   @Override
    public MemberDto findByUserid(String userid) {
       return memberDao.findByUserid(userid);
   }

   @Override
    public  MemberDto getMemberInfo(String userid) {
       MemberDto dto = new MemberDto();
       dto = memberDao.findByUserid(userid);
       return dto;
   }

   @Override
    public MemberDto editMemberInfo(MemberDto memberDto) {
       MemberDto org = new MemberDto();
       org = memberDao.findByUserid(memberDto.getUserid());

       if (memberDto.getUserpw().length() <= 20)
           memberDto.setUserpw(passwordEncoder.encode(memberDto.getUserpw()));
       if (!memberDto.getUserpw().equals(org.getUserpw()))
           memberDao.updateUserpw(memberDto.getUserid(), memberDto.getUserpw());
       if(!memberDto.getUsername().equals(org.getUsername()))
           memberDao.updateUsername(memberDto.getUserid(), memberDto.getUsername());
       if (!memberDto.getTelnumber().equals(org.getTelnumber()))
           memberDao.updateTelnumber(memberDto.getUserid(), memberDto.getTelnumber());
       if (!memberDto.getAddr().equals(org.getAddr()))
           memberDao.updateAddr(memberDto.getUserid(), memberDto.getAddr());

       memberDto = memberDao.findByUserid(memberDto.getUserid());

       return memberDto;

    }

    @Override
    public void unregistUser(String userid) {
        memberDao.deleteUser(userid);
    }

    @Override
    public List<MemberDto> getMemberList() {
        List<MemberDto> list = new ArrayList<>();
        list = memberDao.selectMember();
        return list;
    }

    @Override
    public void editUser(MemberDto dto) {
        MemberDto org = new MemberDto();
        org = memberDao.findByUserid(dto.getUserid());
        String chngPw="";

        // 뷰로 입력받은 패스워드인지 확인하여 암호화
        if(dto.getUserpw().length() <= 20)
            chngPw = passwordEncoder.encode(dto.getUserpw());
        // 원본 패스워드와 뷰 패스워드가 다르면 패스워드 업데이트
        if(!dto.getUserpw().equals(org.getUserpw()))
            memberDao.updateUserpw(dto.getUserid(),chngPw);
        // 원본 이름와 뷰 이름이 다르면 이름 업데이트
        if(dto.getPermit() != org.getPermit())
            memberDao.updatePermit(dto.getUserid(), dto.getPermit());
    }

    @Override
    public void deleteUser(String userid) {
        memberDao.deleteUser(userid);
    }

    @Override
    public String getMemberByName(MemberDto dto) {
        return memberDao.selectUserId(dto);
    }
   }

