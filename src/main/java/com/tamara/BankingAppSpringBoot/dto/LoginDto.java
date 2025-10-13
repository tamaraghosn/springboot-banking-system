package com.tamara.BankingAppSpringBoot.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

   private String email;
   private String password;
}
