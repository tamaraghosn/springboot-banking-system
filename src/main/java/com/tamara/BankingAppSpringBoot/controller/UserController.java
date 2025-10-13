package com.tamara.BankingAppSpringBoot.controller;


import com.tamara.BankingAppSpringBoot.dto.*;
import com.tamara.BankingAppSpringBoot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag( name = "User Operations", description = "Endpoints for managing users and accounts")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(
            summary = "Create a new account",
            description = "Registers a new user and generates an account number."
    )
    @ApiResponse(responseCode = "201", description = "Account successfully created")
    @PostMapping("/create")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }


    @PostMapping("/login")
    public  BankResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }


    @Operation(
            summary = "Balance Inquiry",
            description = "Check the balance of a specific account using its account number."
    )
    @GetMapping("/user/balanceInquiry/{accountNumber}")
    public BankResponse balanceInquiry(@PathVariable String accountNumber) {
        InquiryRequest request = new InquiryRequest(accountNumber);
        return userService.balanceInquiry(request);
    }



    @Operation(
            summary = "Name Inquiry",
            description = "Retrieve the account holder’s full name using an account number."
    )
    @GetMapping("/user/nameInquiry/{accountNumber}")
    public String nameInquiry(@PathVariable String accountNumber) {
        InquiryRequest request = new InquiryRequest(accountNumber);
        return userService.nameInquiry(request);
    }


    @Operation(
            summary = "Credit an account",
            description = "Adds funds to a user account. Requires account number and amount."
    )
    @ApiResponse(responseCode = "200", description = "Account credited successfully c")
    @PostMapping("/user/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }



    @Operation(
            summary = "Debit an account",
            description = "Withdraws funds from a user account. Validates balance before debiting."
    )
    @ApiResponse(responseCode = "200", description = "Account debited successfully")
    @PostMapping("/user/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }



    @Operation(
            summary = "Transfer funds",
            description = "Transfers money between two accounts. Requires source, destination, and amount."
    )
    @ApiResponse(responseCode = "200", description = "Transfer completed successfully")
    @PostMapping("/user/transfer")
    public BankResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }


//    @GetMapping("/user/balanceInquiry")
//    public BankResponse balanceInquiry(@RequestBody InquiryRequest request){
//        return userService.balanceInquiry(request);
//    }
//
//    @GetMapping("/user/nameInquiry")
//    public String nameInquiry(@RequestBody InquiryRequest request){
//        return userService.nameInquiry(request);
//    }



}
