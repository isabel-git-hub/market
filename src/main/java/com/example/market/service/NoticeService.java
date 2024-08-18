package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dto.NoticeDto;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {
    List<NoticeDto> getAllNotices() throws DataAccessException;

    NoticeDto getNoticeById(Long id);

    void writeNotice(NoticeDto noticeDto);

    void updateNotice(NoticeDto noticeDto);

    void deleteNotice(Long id);

    void incrementHitCount(Long id);

    Page<NoticeDto> getNoticesWithPaging(Search search);

    int getTotalNoticeCount();

}
