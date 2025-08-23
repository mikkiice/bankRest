package com.example.bankcards.repository;

import com.example.bankcards.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByToCard_CardNumber(String cardNumber, Pageable pageable);
    Page<Transaction> findAllByFromCard_CardNumber(String cardNumber, Pageable pageable);
    Page<Transaction> findAllByFromCard_User_UsernameOrToCard_User_Username(
            String fromUsername,
            String toUsername,
            Pageable pageable
    );



}
