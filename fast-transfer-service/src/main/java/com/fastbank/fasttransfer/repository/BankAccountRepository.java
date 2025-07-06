package com.fastbank.fasttransfer.repository;

import com.fastbank.fasttransfer.entity.BankAccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Bank Account Repository
 */
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    boolean existsByIban(final String iban);

    @Query("select a from BankAccountEntity a where a.iban = :iban")
    Optional<BankAccountEntity> findByAccountNumber(@Param("iban") final String iban);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BankAccountEntity findByIban(final String iban);
}
