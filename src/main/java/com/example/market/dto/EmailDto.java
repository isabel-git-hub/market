package com.example.market.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDto {
    private String to;
    private String from;
    private String subject;
    private String text;
}
