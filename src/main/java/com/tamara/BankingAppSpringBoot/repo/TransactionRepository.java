package com.tamara.BankingAppSpringBoot.repo;

import com.tamara.BankingAppSpringBoot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {


}
