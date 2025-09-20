package com.tamara.BankingAppSpringBoot.service.imp;

import com.tamara.BankingAppSpringBoot.dto.AccountInfo;
import com.tamara.BankingAppSpringBoot.dto.BankResponse;
import com.tamara.BankingAppSpringBoot.dto.UserRequest;
import com.tamara.BankingAppSpringBoot.entity.User;
import com.tamara.BankingAppSpringBoot.repo.UserRepository;
import com.tamara.BankingAppSpringBoot.service.UserService;
import com.tamara.BankingAppSpringBoot.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImp implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        /* CHECK IF USER ALREADY HAS AN ACCOUNT bY EMAIL */
        if (userRepository.existsByEmail(userRequest.getEmail())) {

           return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                   .accountInfo(null)
                    .build();

        }

        /*Creating an account - saving a new user into the db */
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
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

        User savedUser = userRepository.save(newUser);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }
}
