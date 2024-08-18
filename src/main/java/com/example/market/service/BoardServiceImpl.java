package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dao.BoardDao;
import com.example.market.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardDao boardDao;

    // 게시판 목록 조회 (페이징 적용)
    @Override
    public Page<BoardDto> getBoardsByTypeWithPaging(String boardType, Search search) {
        // 총 데이터 개수 조회
        int totalDataCount = getTotalBoardCountByType(boardType);

        // 페이징 처리를 위해 DAO에서 데이터 가져오기
        List<BoardDto> boards = boardDao.findBoardsByTypeWithPaging(boardType, search.getOffset(), search.getRecordSize());

        // PageImpl 객체를 이용해 Page<BoardDto> 반환
        return new PageImpl<>(boards, PageRequest.of(search.getPage() - 1, search.getRecordSize()), totalDataCount);
    }

    // 게시판 목록 조회
    @Override
    public int getTotalBoardCountByType(String boardType) {
        return boardDao.countBoardsByType(boardType);
    }

    // 특정 게시판 조회
    @Override
    public BoardDto getBoardById(Long id) {
        return boardDao.findBoardById(id);
    }

    // 게시판 생성
    @Transactional
    @Override
    public void createBoard(BoardDto boardDto) {
        boardDao.insertBoard(boardDto);
    }

    // 게시판 수정
    @Transactional
    @Override
    public void updateBoard(BoardDto boardDto) {
        boardDao.updateBoard(boardDto);
    }

    @Override
    public void incrementHitCount(Long id) {
        boardDao.incrementHitCount(id);
    }
    // 게시판 삭제
    @Transactional
    @Override
    public void deleteBoard(String boardType) {
        boardDao.deleteBoard(boardType);
    }

}
