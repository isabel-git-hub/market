package com.example.market.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private String userid;
    private String userpw;
    private String username;
    private String birthdate;
    private String gender;
    private String telnumber;
    private String addr;
    private String email;
    private int permit;
//    private String role;
}
