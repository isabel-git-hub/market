package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dto.BoardDto;
import org.springframework.data.domain.Page;

public interface BoardService {

    Page<BoardDto> getBoardsByTypeWithPaging(String boardType, Search search);

    int getTotalBoardCountByType(String boardType);

    BoardDto getBoardById(Long id);

    void createBoard(BoardDto boardDto);

    void updateBoard(BoardDto boardDto);

    void incrementHitCount(Long id);

    void deleteBoard(String boardType);

}

