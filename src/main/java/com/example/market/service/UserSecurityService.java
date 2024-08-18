package com.example.market.service;

import com.example.market.dao.MemberDao;
import com.example.market.dto.MemberDto;
import com.example.market.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    private final MemberDao dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSecurityService(MemberDao dao, @Lazy PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDto create(MemberDto dto) {
        dto.setUserpw(passwordEncoder.encode(dto.getUserpw()));
        this.dao.insertMember(dto);
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        MemberDto member = this.dao.findByUserid(userid);
        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (member.getPermit() == 9) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(member.getUserid(), member.getUserpw(), authorities);
    }
}
