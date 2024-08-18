package com.example.market.controller;

import com.example.market.dto.EmailDto;
import com.example.market.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {
    private final EmailService email;
    @Autowired
    public EmailController(EmailService email) {
        this.email = email;
    }

    @GetMapping("/inquire")
    public String inquireForm() {
        return "inquireForm";
    }

    @PostMapping("/sendMail")
    public String sendMail(EmailDto msg) {
        if(email.sendMailReject(msg))
            System.out.println("Email 전송 성공!!!");
        else System.out.println("Email 전송 실패!!!");
        return "redirect:/";
    }
}
