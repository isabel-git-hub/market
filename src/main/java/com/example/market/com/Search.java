package com.example.market.com;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
    /***** 페이징 영역 *****/
    private int page;                 // 현재 페이지
    private int recordSize;           // 페이지당 보여줄 레코드 수
    private int pageSize;             // 한번에 표시할 페이지 수
    private int totDataCnt;           // 전체 데이터 수
    private int totPageCnt;           // 전체 페이지 수
    private boolean existPrevPage;    // 이전 페이지 존재 여부
    private boolean existNextPage;    // 다음 페이지 존재 여부
    private int startPage;            // 페이지 리스트 시작 번호
    private int endPage;              // 페이지 리스트 끝 번호

    /***** 검색 영역 *****/
    private String keyword;           // 검색 키워드
    private String searchType;        // 검색 방법

    public Search() {}

    public Search(int recordSize, int pageSize) {
        this.page = 1;
        this.recordSize = recordSize;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (page - 1) * recordSize;
    }

    public void calcPage(int totDataCnt) {
        this.totDataCnt = totDataCnt;
        totPageCnt = ((totDataCnt - 1) / recordSize) + 1;
        startPage = ((page - 1) / pageSize) * pageSize + 1;
        endPage = startPage + pageSize - 1;
        if (endPage > totPageCnt) endPage = totPageCnt;
        existPrevPage = startPage != 1;
        existNextPage = endPage != totPageCnt;
    }
}
