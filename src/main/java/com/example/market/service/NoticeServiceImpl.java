package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dao.NoticeDao;
import com.example.market.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDao noticeDao;

    @Override
    public List<NoticeDto> getAllNotices() {
        return noticeDao.selectAllNotices();
    }

    @Override
    public NoticeDto getNoticeById(Long id) {
        return noticeDao.selectNoticeById(id);
    }

    @Override
    public void writeNotice(NoticeDto noticeDto) {
        noticeDao.insertNotice(noticeDto);
    }

    @Override
    public void updateNotice(NoticeDto noticeDto) {
        noticeDao.updateNotice(noticeDto);
    }

    @Override
    public void deleteNotice(Long id) {
        noticeDao.deleteNotice(id);
    }

    @Override
    public void incrementHitCount(Long id) {
        noticeDao.incrementHitCount(id);
    }

    @Override
    public Page<NoticeDto> getNoticesWithPaging(Search search) {
        int totalDataCount = getTotalNoticeCount();  // 총 공지사항 수 가져오기
        List<NoticeDto> notices = noticeDao.findNoticesWithPaging(search.getOffset(), search.getRecordSize());
        return new PageImpl<>(notices, PageRequest.of(search.getPage() - 1, search.getRecordSize()), totalDataCount);
    }

    @Override
    public int getTotalNoticeCount() {
        return noticeDao.countAllNotices();
    }
}
