package com.fastbank.fasttransfer.repository;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CurrencyRepository {

    public boolean isValidCurrency(String currency) {
        return Set.of("EUR", "USD").contains(currency);
    }
}
