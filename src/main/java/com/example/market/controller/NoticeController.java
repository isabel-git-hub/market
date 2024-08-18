package com.example.market.controller;

import com.example.market.com.Search;
import com.example.market.dto.NoticeDto;
import com.example.market.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 목록 페이지 (페이징 적용)
    @GetMapping
    public String table(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        Model model) {
        Search search = new Search(size, 5);
        // 1페이지, 한 페이지에 10개 공지사항
        search.setPage(page);
        search.setKeyword("");

        // 전체 공지사항 개수 조회
        int totalDataCount = noticeService.getTotalNoticeCount();  // 전체 공지사항 개수를 조회하는 메서드가 필요합니다.
        search.calcPage(totalDataCount);

        // 페이징 처리된 공지사항 목록 조회
        Page<NoticeDto> noticePage = noticeService.getNoticesWithPaging(search);
        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("search", search);
//        model.addAttribute("currentPage", page);  // 현재 페이지를 모델에 추가
        return "notices/table";
    }

    // 공지사항 상세 페이지
    @GetMapping("/{id}")
    public String inform(@PathVariable Long id, Model model) {
        noticeService.incrementHitCount(id);

        NoticeDto notice = noticeService.getNoticeById(id);
        if (notice == null) {
            return "redirect:/notice";
        }
        model.addAttribute("notice", notice);
        return "notices/inform";
    }

    // 공지사항 작성 페이지
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("noticeDto", new NoticeDto());
        return "notices/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
        // noticeDto.setAuthor(user.getUsername());
        noticeService.writeNotice(noticeDto);
        redirectAttributes.addFlashAttribute("message", "공지사항이 등록되었습니다.");
        return "redirect:/notice";
    }

    // 공지사항 수정 페이지
    @GetMapping("/alter/{id}")
    public String alterForm(@PathVariable Long id, Model model) {
        // NoticeDto notice = noticeService.getNoticeById(id);
        // if (user == null || !user.getUsername().equals(notice.getAuthor())) {
        //     return "redirect:/login";
        // }
        NoticeDto notice = noticeService.getNoticeById(id);
        if (notice == null) {
            return "redirect:/notice";
        }
        model.addAttribute("noticeDto", notice);
        return "notices/alter";
    }

    @PostMapping("/alter")
    public String updateNotice(@ModelAttribute NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
        // if (user == null || !user.getUsername().equals(noticeDto.getAuthor())) {
        //     return "redirect:/login";
        // }
        noticeService.updateNotice(noticeDto);
        redirectAttributes.addFlashAttribute("message", "공지사항이 수정되었습니다.");
        return "redirect:/notice/" + noticeDto.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        // noticeService.deleteNotice(id);
        redirectAttributes.addFlashAttribute("message", "공지사항이 삭제되었습니다.");
        return "redirect:/notice";
    }
}
