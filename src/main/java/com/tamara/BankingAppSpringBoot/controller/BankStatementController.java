package com.tamara.BankingAppSpringBoot.controller;


import com.tamara.BankingAppSpringBoot.entity.Transaction;
import com.tamara.BankingAppSpringBoot.service.imp.BankStatement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statements")
@AllArgsConstructor
public class BankStatementController {

    @Autowired
    private BankStatement bankStatement;


//    GET http://localhost:8080/api/statements/123456789?startDate=2025-01-01&endDate=2025-01-31
    @GetMapping("/{accountNumber}")
    public List<Transaction> generateStatement(
            @PathVariable String accountNumber,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {

        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }


}
