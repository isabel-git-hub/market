package com.example.market.service;

import com.example.market.dto.EmailDto;
import com.example.market.dto.MemberDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final String FROM_MAIL_GOOGLE = "wonyuchung@gmail.com";

    public boolean sendMailReject(EmailDto mail) {
        boolean msg = true;

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setFrom(FROM_MAIL_GOOGLE);
            helper.setText(mail.getText(), true);

            javaMailSender.send(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            msg = false;
        }
        return msg;
    }

    public boolean makeMsgTmpPw(MemberDto dto) {
        boolean result = false;
        EmailDto mail = new EmailDto();
        mail.setSubject("임시 비밀번호를 발행했습니다.");
        mail.setTo(dto.getEmail());

        String text = "로그인을 위한 임시 비밀번호를 발행했습니다.<br><br>"
                    + "임시 비밀번호 : " + dto.getUserpw() + "<br><br>"
                    + "로그인 후 반드시 비밀번호 변경을 해주세요.<br><br>";
        mail.setText(text);

        result = sendMailReject(mail);

        return result;
    }
}

