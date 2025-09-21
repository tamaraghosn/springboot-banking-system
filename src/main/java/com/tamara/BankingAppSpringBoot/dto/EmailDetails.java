package com.tamara.BankingAppSpringBoot.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    // building a full email service in case for the future
    // for now we're just gonna be sending alerts


    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;


}
