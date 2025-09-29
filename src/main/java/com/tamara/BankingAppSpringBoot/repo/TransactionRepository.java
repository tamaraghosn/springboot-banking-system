package com.tamara.BankingAppSpringBoot.repo;

import com.tamara.BankingAppSpringBoot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByAccountNumberAndCreatedAtBetween(
                  String accountNumber,
                  LocalDate startDate,
                  LocalDate endDate
    );

}
