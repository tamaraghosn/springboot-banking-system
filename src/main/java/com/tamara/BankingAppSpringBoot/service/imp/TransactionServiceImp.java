package com.tamara.BankingAppSpringBoot.service.imp;

import com.tamara.BankingAppSpringBoot.dto.TransactionDto;
import com.tamara.BankingAppSpringBoot.entity.Transaction;
import com.tamara.BankingAppSpringBoot.repo.TransactionRepository;
import com.tamara.BankingAppSpringBoot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImp implements TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {

        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .amount(transactionDto.getAmount())
                .accountNumber(transactionDto.getAccountNumber())
                .status("SUCCESS")
                .build();

        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully");


    }
}
