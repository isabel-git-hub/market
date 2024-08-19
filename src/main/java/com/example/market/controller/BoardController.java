package com.example.market.controller;

import com.example.market.com.Search;
import com.example.market.dto.BoardDto;
import com.example.market.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {
    private final BoardService boardService;

    // 게시판 목록 페이지 (페이징 및 검색 처리)
    @GetMapping("/{boardType}/list")
    public String listBoards(@PathVariable String boardType,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model) {
        Search search = new Search(size, 5);  // 페이지당 10개, 페이지 리스트 5개
        search.setPage(page);
        search.setKeyword("");

        // 전체 게시판 개수 조회
        int totalDataCount = boardService.getTotalBoardCountByType(boardType);
        search.calcPage(totalDataCount);

        // 페이징 처리된 게시판 목록 조회
        Page<BoardDto> boardPage = boardService.getBoardsByTypeWithPaging(boardType, search);
        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("boardType", boardType);


        return "board/" + boardType + "/list"; // 게시판 목록 페이지
    }

    // 게시판 상세 페이지
    @GetMapping("/{boardType}/{id}")
    public String viewBoard(@PathVariable String boardType, @PathVariable Long id, Model model) {
        try {
            boardService.incrementHitCount(id);
            BoardDto board = boardService.getBoardById(id);
            if (board == null) {
                return "redirect:/board/" + boardType + "/list";  // 게시판이 없으면 목록으로 리다이렉트
            }
            model.addAttribute("board", board);
            model.addAttribute("boardType", boardType);
            return "board/" + boardType + "/view"; // 게시판 상세 페이지
        } catch (Exception e) {
            model.addAttribute("error", "게시판을 가져오는 데 실패했습니다.");
            return "error";
        }
    }


    // 게시글 작성 폼 페이지
    @GetMapping("/{boardType}/new")
    public String createBoardForm(@PathVariable String boardType, Model model) {
        model.addAttribute("boardDto", new BoardDto());
        model.addAttribute("boardType", boardType);
        return "board/" + boardType + "/new"; // 게시판 작성 폼 페이지
    }

    // 게시판 등록 처리
    @PostMapping("/{boardType}/new")
    public String createBoard(@PathVariable String boardType, @ModelAttribute BoardDto boardDto, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // 세션에서 로그인 사용자 정보 가져오기
            String loggedInUser = (String) session.getAttribute("loggedInUser");
            if (loggedInUser != null) {
                boardDto.setAuthor(loggedInUser);  // 로그인 사용자 이름을 작성자로 설정
            }

            // 게시글 정보 설정
            boardDto.setBoardType(boardType); // 게시판 유형 설정
            boardService.createBoard(boardDto);
            redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
            return "redirect:/board/" + boardType + "/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "게시글 등록에 실패했습니다.");
            return "redirect:/board/" + boardType + "/new";
        }
    }

    // 게시판 수정 폼 페이지
    @GetMapping("/{boardType}/edit/{id}")
    public String editBoardForm(@PathVariable String boardType, @PathVariable Long id, Model model) {
        try {
            BoardDto board = boardService.getBoardById(id);
            if (board == null) {
                return "redirect:/board/" + boardType + "/list";
            }
            model.addAttribute("boardDto", board);
            model.addAttribute("boardType", boardType);
            return "board/" + boardType + "/edit";  // 게시판 수정 폼 페이지
        } catch (Exception e) {
            model.addAttribute("error", "게시판 수정 페이지를 가져오는 데 실패했습니다.");
            return "error";
        }
    }

    // 게시판 수정 처리
    @PostMapping("/{boardType}/edit")
    public String updateBoard(@PathVariable String boardType, @ModelAttribute BoardDto boardDto, RedirectAttributes redirectAttributes) {
        try {
            boardDto.setBoardType(boardType);  // 게시판 유형 설정
            boardService.updateBoard(boardDto);
            redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
            return "redirect:/board/" + boardType + "/" + boardDto.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "게시글 수정에 실패했습니다.");
            return "redirect:/board/" + boardType + "/edit/" + boardDto.getId();
        }
    }

    // 게시판 삭제 처리
    @PostMapping("/{boardType}/delete/{id}")
    public String deleteBoard(@PathVariable String boardType, @PathVariable Long id) {
        try {
             boardService.deleteBoard(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/board/" + boardType + "/list";
    }
}
