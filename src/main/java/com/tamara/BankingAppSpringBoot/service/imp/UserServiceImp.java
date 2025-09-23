package com.tamara.BankingAppSpringBoot.service.imp;

import com.tamara.BankingAppSpringBoot.dto.*;
import com.tamara.BankingAppSpringBoot.entity.User;
import com.tamara.BankingAppSpringBoot.repo.UserRepository;
import com.tamara.BankingAppSpringBoot.service.EmailService;
import com.tamara.BankingAppSpringBoot.service.UserService;
import com.tamara.BankingAppSpringBoot.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImp implements UserService {


    @Autowired
    UserRepository userRepository;


    @Autowired
    EmailService emailService;


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
        // Send email Alert

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Welcome! Your New Bank Account Details")
                .messageBody(  "Dear " + savedUser.getFirstName() + ",\n\n" +
                        "We are pleased to inform you that your bank account has been successfully created.\n\n" +
                        "Here are your account details:\n" +
                        "---------------------------------\n" +
                        "Account Name   : " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n" +
                        "Account Number : " + savedUser.getAccountNumber() + "\n" +
                        "---------------------------------\n\n" +
                        "Thank you for choosing our banking services.\n\n" +
                        "Best regards,\n" +
                        "Your Banking App Team")
                .build();
//                .messageBody("Congratulations! Your Account Has been Successfully Created.\n Your Account Details: \n " +
//                        "Account Name: " + savedUser.getFirstName() + " " +
//                        savedUser.getLastName() + " " + savedUser.getOtherName() + "\nAccount Number; "
//                        + savedUser.getAccountNumber()
//                        )


        emailService.sendEmailAlerts(emailDetails);

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

    @Override
    public BankResponse balanceInquiry(InquiryRequest inquiryRequest) {
        // check if the provided account nb exists in the database
        boolean isAccountExist = userRepository.existsByAccountNumber(inquiryRequest.getAccountNumber());

        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(inquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(inquiryRequest.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " "+ foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameInquiry(InquiryRequest inquiryRequest) {
        // check if the provided account nb exists in the database
        boolean isAccountExist = userRepository.existsByAccountNumber(inquiryRequest.getAccountNumber());

        if (!isAccountExist){

            return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(inquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }
}
