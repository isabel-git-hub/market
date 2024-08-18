package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dao.EventDao;
import com.example.market.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    @Override
    public List<EventDto> getAllEvents() {
        return eventDao.findAll();
    }

    @Override
    public EventDto getEventById(Long id) {
        return eventDao.findById(id);
    }

    @Override
    public void saveEvent(EventDto event) {
        if (event.getId() != null) {
            eventDao.update(event);
        } else {
            eventDao.insert(event);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        eventDao.delete(id);
    }

    @Override
    public void incrementHitCount(Long id) {
        eventDao.incrementHitCount(id);
    }

    @Override
    public Page<EventDto> getEventsWithPaging(Search search) {
        int totalDataCount = eventDao.countAllEvents();  // 전체 이벤트 개수 조회
        search.calcPage(totalDataCount);  // 페이지 정보 계산

        List<EventDto> events = eventDao.findEventsWithPaging(search.getOffset(), search.getRecordSize());
        return new PageImpl<>(events, PageRequest.of(search.getPage() - 1, search.getRecordSize()), totalDataCount);
    }
    @Override
    public int getTotalEventCount() {
        return eventDao.countAllEvents();  // 전체 이벤트 개수 조회
    }

}
