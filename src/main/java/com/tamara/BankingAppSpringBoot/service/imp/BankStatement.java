package com.tamara.BankingAppSpringBoot.service.imp;


import com.tamara.BankingAppSpringBoot.entity.Transaction;
import com.tamara.BankingAppSpringBoot.repo.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {

    @Autowired
    private TransactionRepository transactionRepository;


    /*Retrieve list of transactions wihtin a date range given an account number
    *generate a pdf file of transcations
    * send the file via email
    * */

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        return transactionRepository.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);
//        List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
//                .filter(transaction -> transaction.getCreatedAt().isAfter(start))
//                .filter(transaction -> transaction.)


    }

}
