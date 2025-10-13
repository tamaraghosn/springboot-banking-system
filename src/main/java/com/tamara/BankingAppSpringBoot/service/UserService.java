package com.tamara.BankingAppSpringBoot.service;

import com.tamara.BankingAppSpringBoot.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);

    BankResponse login(LoginDto loginDto);

    BankResponse balanceInquiry(InquiryRequest inquiryRequest);

    String nameInquiry(InquiryRequest inquiryRequest);

    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);

    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);

    BankResponse transfer(TransferRequest transferRequest);

}
