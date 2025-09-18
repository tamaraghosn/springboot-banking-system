package com.tamara.BankingAppSpringBoot.service.imp;

import com.tamara.BankingAppSpringBoot.dto.BankResponse;
import com.tamara.BankingAppSpringBoot.dto.UserRequest;
import com.tamara.BankingAppSpringBoot.entity.User;
import com.tamara.BankingAppSpringBoot.service.UserService;
import com.tamara.BankingAppSpringBoot.utils.AccountUtils;

import java.math.BigDecimal;

public class UserServiceImp implements UserService {
    @Override

    public BankResponse createAccount(UserRequest userRequest) {

        /* CHECK IF USER ALREADY HAS AN ACCOUNT bY EMAIL */



        /*Creating an account - saving a new user into the db */
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getGender())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativeNumber(userRequest.getAlternativeNumber())
                .status("ACTIVE")
                .build();
    }
}
