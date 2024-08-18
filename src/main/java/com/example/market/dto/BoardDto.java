package com.example.market.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String description;
    private String author; // 작성자
    private LocalDateTime createdDate; // 작성일
    private LocalDateTime updatedDate;
    private int hitCount; // 조회수
    private String boardType;
}
