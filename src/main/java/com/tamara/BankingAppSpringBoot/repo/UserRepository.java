package com.tamara.BankingAppSpringBoot.repo;

import com.tamara.BankingAppSpringBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
