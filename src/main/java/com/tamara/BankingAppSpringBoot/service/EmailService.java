package com.tamara.BankingAppSpringBoot.service;

import com.tamara.BankingAppSpringBoot.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlerts(EmailDetails emailDetails);

    void  sendEmailWithAttachment(EmailDetails emailDetails);

}
