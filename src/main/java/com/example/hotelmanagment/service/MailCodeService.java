package com.example.hotelmanagment.service;

import com.example.hotelmanagment.entity.MailCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MailCodeService {
    void saveMailCode(MailCode mailCode);
    void updateMailCode(MailCode mailCode);
    void deleteMailCode(MailCode mailCode);
    List<MailCode> getMailCodes();
    MailCode getMailCode(int id);
}
