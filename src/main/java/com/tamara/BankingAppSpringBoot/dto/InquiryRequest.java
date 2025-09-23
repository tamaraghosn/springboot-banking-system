package com.tamara.BankingAppSpringBoot.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryRequest {

//    inquire the balance of a specific account number
//    inquire the name of a specific account nb

    private String accountNumber;


}
