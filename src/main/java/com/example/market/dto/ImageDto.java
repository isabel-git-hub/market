package com.example.market.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ImageDto {
    private Long id; // 파일 ID
    private byte[] imageData; // 이미지 바이너리 데이터
    private LocalDateTime createdDate; // 생성 날짜
}
