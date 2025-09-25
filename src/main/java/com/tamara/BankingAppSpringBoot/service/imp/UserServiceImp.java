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

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());

        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }


        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));

        userRepository.save(userToCredit);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .accountNumber(userToCredit.getAccountNumber())
                        .accountBalance(userToCredit.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {

        // Check if the account exists
        boolean isAccountExist = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());

        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

        // Check if the balance is sufficient
        if (userToDebit.getAccountBalance().compareTo(creditDebitRequest.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .accountNumber(userToDebit.getAccountNumber())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }

        // Debit the account
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
        // update DB
        userRepository.save(userToDebit);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                        .accountNumber(userToDebit.getAccountNumber())
                        .accountBalance(userToDebit.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        // get the account i'm debiting from
        // check if the amount is not more the current account's balance
        // debit the account
        // get the account i'm crediting
        //  credit the account

        User sourceAccountUser = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());

        if (sourceAccountUser == null) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage("Source Account does not exist! ")
                    .accountInfo(null)
                    .build();
        }

        if(sourceAccountUser.getAccountBalance().compareTo(transferRequest.getAmount())<0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User destinationAccountUser = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());

        if (destinationAccountUser==null){
            return BankResponse.builder()
                    .responseMessage("Destination account does not exist")
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .accountInfo(null)
                    .build();

        }

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));

        userRepository.save(sourceAccountUser);
        userRepository.save(destinationAccountUser);

        // alert the account that's sending the money

        EmailDetails debitAlert = EmailDetails.builder()
                .recipient(sourceAccountUser.getEmail())
                .subject("Transfer Debit Alert")
                .messageBody("Dear " + sourceAccountUser.getFirstName() + ",\n\n" +
                        "You have transferred " + transferRequest.getAmount() +
                        " to account " + destinationAccountUser.getAccountNumber() + ".\n" +
                        "Your new balance is " + sourceAccountUser.getAccountBalance() + ".")
                .build();
        emailService.sendEmailAlerts(debitAlert);

        // alert the account thats receiving money


        EmailDetails creditAlert = EmailDetails.builder()
                .recipient(destinationAccountUser.getEmail())
                .subject("Transfer Credit Alert")
                .messageBody("Dear " + destinationAccountUser.getFirstName() + ",\n\n" +
                        "Your account has been credited with " + transferRequest.getAmount() +
                        " from account " + sourceAccountUser.getAccountNumber() + ".\n" +
                        "Your new balance is " + destinationAccountUser.getAccountBalance() + ".")
                .build();

        emailService.sendEmailAlerts(creditAlert);


        return  BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESS_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(sourceAccountUser.getAccountBalance())
                        .accountNumber(sourceAccountUser.getAccountNumber())
                        .accountName(sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName())
                        .build())


                .build();
    }
}
