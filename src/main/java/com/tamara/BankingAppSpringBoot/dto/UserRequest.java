package com.tamara.BankingAppSpringBoot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;

//    //    generated at creation, the bank auto assigns and account nb for the user
//    so we dont need it in the dto class
//    private String accountNumber;
//    we're going to assume the user has zero balance once creating the account
//    private BigDecimal accountBalance;


    private String email;
    private  String phoneNumber;
    private String alternativeNumber;
//    private  String status;


}
