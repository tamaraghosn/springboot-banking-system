package com.tamara.BankingAppSpringBoot.service;

import com.tamara.BankingAppSpringBoot.dto.BankResponse;
import com.tamara.BankingAppSpringBoot.dto.CreditDebitRequest;
import com.tamara.BankingAppSpringBoot.dto.InquiryRequest;
import com.tamara.BankingAppSpringBoot.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceInquiry(InquiryRequest inquiryRequest);

    String nameInquiry(InquiryRequest inquiryRequest);

    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);

}
