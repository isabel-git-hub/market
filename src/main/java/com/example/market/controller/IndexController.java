package com.example.market.controller;

import com.example.market.com.Search;
import com.example.market.config.BoardTypeConstants;
import com.example.market.dto.BoardDto;
import com.example.market.dto.EventDto;
import com.example.market.dto.FileDto;
import com.example.market.dto.NoticeDto;
import com.example.market.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final ProductService productService;
    private final BoardService boardService;
    private final EventService eventService;
    private final NoticeService noticeService;
    @GetMapping({"/", "/index"})
    public String indexPage(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("greeting", "안녕하세요, " + loggedInUser + "님");
        }

        // 상품 목록 데이터 추가
        List<FileDto> products = productService.getLatestProducts();
        model.addAttribute("products", products);

        // 출석체크 데이터 추가
        List<BoardDto> boards = boardService.getAllBoardsByType(BoardTypeConstants.WELCOME);
        model.addAttribute("boards", boards);

        // 이벤트 데이터 추가
        List<EventDto> events = eventService.getAllEvents();
        model.addAttribute("events", events);

        // 공지사항 데이터 추가
        List<NoticeDto> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);

        return "index";
    }
}
