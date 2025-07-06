package com.fastbank.fasttransfer.repository;

import com.fastbank.fasttransfer.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Currency Repository
 */
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    boolean existsByCode(final String code);
}
