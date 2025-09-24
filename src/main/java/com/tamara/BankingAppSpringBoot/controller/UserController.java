package com.tamara.BankingAppSpringBoot.controller;


import com.tamara.BankingAppSpringBoot.dto.BankResponse;
import com.tamara.BankingAppSpringBoot.dto.CreditDebitRequest;
import com.tamara.BankingAppSpringBoot.dto.InquiryRequest;
import com.tamara.BankingAppSpringBoot.dto.UserRequest;
import com.tamara.BankingAppSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/user/balanceInquiry/{accountNumber}")
    public BankResponse balanceInquiry(@PathVariable String accountNumber) {
        InquiryRequest request = new InquiryRequest(accountNumber);
        return userService.balanceInquiry(request);
    }

    @GetMapping("/user/nameInquiry/{accountNumber}")
    public String nameInquiry(@PathVariable String accountNumber) {
        InquiryRequest request = new InquiryRequest(accountNumber);
        return userService.nameInquiry(request);
    }

    @PostMapping("/user/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
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
