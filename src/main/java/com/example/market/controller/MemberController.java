package com.example.market.controller;

import com.example.market.dto.MemberDto;
//import com.example.market.service.EmailService;
import com.example.market.service.EmailService;
import com.example.market.service.MemberService;
import com.example.market.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final UserSecurityService userService;
    private final EmailService mailService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(Model model) {
//        model.addAttribute("page", "login");
        model.addAttribute("title", "로그인");
        return "login";
    }

/*
    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("userid") String userid,
                        @RequestParam("userpw") String userpw,
                        HttpSession session, Model model) {
        // 로그인 확인
        if (memberService.checkLogin(userid, userpw)) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", userid);
            return "redirect:/index";  // 로그인 성공 시 리다이렉션
        } else {
            model.addAttribute("error", "아이디 또는 패스워드가 잘못되었습니다.");
            return "login";  // 로그인 실패 시 로그인 페이지로 리턴
        }
        // 로그인 실패 시 에러 메시지 전달
//        return "redirect:/login?error";  // 로그인 실패 시 에러 파라미터 추가
    }
*/
    // 회원가입 페이지
    @GetMapping("/join")
    public String joinPage(Model model) {
//        model.addAttribute("page", "join");
        model.addAttribute("title","회원가입");
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinAply(MemberDto memberDto) {
        userService.create(memberDto);
        return "login";
        // 회원가입 시 중복 체크
//        if (memberService.checkMember(memberDto)) {
//            return "redirect:/join";  // 중복일 경우 회원가입 페이지로 리다이렉션
//        }
        // 회원가입 처리
//        memberService.putMember(memberDto);
//        return "redirect:/login";  // 회원가입 후 로그인 페이지로 리다이렉션
    }

    // 아이디 중복 체크
    @GetMapping("/checkid")
    @ResponseBody
    public String checkId(@RequestParam(value = "data") String userid) {
        return String.valueOf(memberService.checkId(userid));
    }

    // 회원정보 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member")
    public ModelAndView getMemberInfo(Principal principal) {
        ModelAndView mav = new ModelAndView("member");
        MemberDto dto = new MemberDto();
        dto = memberService.getMemberInfo(principal.getName());
        mav.addObject("member", dto);
        return mav;
    }

    // 회원정보 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/member")
    public String editMemberInfo(Model model, MemberDto dto) {
        dto = memberService.editMemberInfo(dto);
        model.addAttribute("member", dto);
        return "member";
    }

    // 회원 탈퇴
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unregist")
    public String unregist(Principal principal) {
        // 회원탈퇴 서비스 요청
        memberService.unregistUser(principal.getName());
        // 로그아웃 리다이렉트
        return "redirect:/logout";
    }

    // 회원관리 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/manage")
    public ModelAndView getMemberList() {
        ModelAndView mav = new ModelAndView("manage");
        List<MemberDto> list = new ArrayList<>();
        list = memberService.getMemberList();
        mav.addObject("memberList", list);
        return mav;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/editUser")
    public String editUserPw(MemberDto dto) {
        System.out.println("***** userid = " + dto.getUserid());
        System.out.println("***** userpw = " + dto.getUserpw());
        System.out.println("***** permit = " + dto.getPermit());

        memberService.editUser(dto);

        return "redirect:/manage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/deleteUser/{userid}")
    public String deleteUser(@PathVariable("userid") String userid) {
        memberService.deleteUser(userid);

        return "redirect:/manage";
    }

    @GetMapping("/findpw")
    public String findPw() {
        return "findPwForm";
    }

    @PostMapping("/findpw")
    public String findpw(Model model, MemberDto dto) {
        String msg = "";

        boolean ck = memberService.checkMember(dto);
        if(ck) msg = "ok";
        else {
            msg = "error";
            model.addAttribute("msg",msg);
            return "findPwForm";
        }

        // 권한 사용자 설정
        dto.setPermit(0);
        // 임시 패스워드 생성
        String tmppw = UUID.randomUUID().toString().substring(0,8);
        // 임시 패스워드 설정
        dto.setUserpw(tmppw);
        memberService.editUser(dto);

        ck = mailService.makeMsgTmpPw(dto);
        if(!ck) {
            msg = "메일 전송에 실패했습니다. 잠시 후 다시 시도해 주세요.";
        }
        msg = "메일을 통해 임시 비밀번호를 전송했습니다. 메일을 확인해 주세요.";
        model.addAttribute("msg",msg);
        return "sendmsg";
    }

    @GetMapping("/findId")
    public String findId() {
        return "findIdForm";
    }

    @PostMapping("/findId")
    public String findId(Model model, MemberDto dto) {
        String msg = "";

        String userid = memberService.getMemberByName(dto);

        if(userid == null) msg = "해당하는 아이디가 존재하지 않습니다.!!!";
        else msg = "당신의 아이디는 " + userid + "입니다.";
        model.addAttribute("msg",msg);
        return "sendmsg";
    }

    // 로그아웃 처리
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        // 세션 무효화
//        session.invalidate();
//        // 로그아웃 후 로그인 페이지로 리다이렉션
//        return "redirect:/login";
//    }
}
