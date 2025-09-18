package com.tamara.BankingAppSpringBoot.service;

import com.tamara.BankingAppSpringBoot.dto.BankResponse;
import com.tamara.BankingAppSpringBoot.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);


}
