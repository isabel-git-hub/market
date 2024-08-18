package com.example.market.controller;

import com.example.market.com.Search;
import com.example.market.dto.EventDto;
import com.example.market.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/event")
@Controller
public class EventController {
    private final EventService eventService;

    @GetMapping
    public String listEvents(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model) {
        Search search = new Search(size, 5);  // 페이지당 레코드 수와 페이지 리스트 크기 설정
        search.setPage(page);  // 현재 페이지 설정
        search.setKeyword("");  // 검색어 초기화 (검색어 기능이 필요한 경우 설정)

        int totalDataCount = eventService.getTotalEventCount();  // 전체 이벤트 개수를 조회하는 메서드 호출
        search.calcPage(totalDataCount);  // 페이지 계산

        Page<EventDto> eventPage = eventService.getEventsWithPaging(search);  // 페이징된 이벤트 목록 조회
        model.addAttribute("events", eventPage.getContent());  // 이벤트 목록 모델에 추가
        model.addAttribute("search", search);  // 검색 및 페이징 정보 모델에 추가

        return "events/list";
    }

    @GetMapping("/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        eventService.incrementHitCount(id);
        EventDto event = eventService.getEventById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "events/view";
        } else {
            return "redirect:/event";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new EventDto());
        return "events/create";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute EventDto event) {
        eventService.saveEvent(event);
        return "redirect:/event";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EventDto event = eventService.getEventById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "events/edit";
        } else {
            return "redirect:/event";
        }
    }

    @PostMapping("/edit")
    public String editEvent(@ModelAttribute EventDto event) {
        eventService.saveEvent(event);
        return "redirect:/event";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/event";
    }
}
