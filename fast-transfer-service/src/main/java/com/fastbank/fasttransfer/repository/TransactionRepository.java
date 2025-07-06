package com.fastbank.fasttransfer.repository;

import com.fastbank.fasttransfer.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Transaction repository
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

}
