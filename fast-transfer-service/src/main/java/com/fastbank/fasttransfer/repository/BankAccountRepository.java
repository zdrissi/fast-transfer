package com.fastbank.fasttransfer.repository;

import com.fastbank.fasttransfer.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Bank Account Repository
 */
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    boolean existsByIban(final String iban);

    Optional<BankAccountEntity> findByIban(final String iban);
}
