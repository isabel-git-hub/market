package com.example.market.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class FileDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;  // 이미지 URL
    private MultipartFile image; // 업로드된 이미지 파일
}
