package com.example.market.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int hitCount;
}