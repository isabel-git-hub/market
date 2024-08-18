package com.example.market.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;             // 상품의 고유 ID, MySQL에서 BIGINT로 매핑됨
    private String name;        // 상품명, MySQL에서 VARCHAR(255)로 매핑됨
    private String description; // 상품 설명, MySQL에서 TEXT로 매핑됨
    private String category;    // 상품 카테고리, MySQL에서 VARCHAR(100)로 매핑됨
    private double price;       // 상품 가격, MySQL에서 DECIMAL(10,2)로 매핑됨
    private String imageUrl;    // 상품 이미지 URL, MySQL에서 VARCHAR(255)로 매핑됨
}
