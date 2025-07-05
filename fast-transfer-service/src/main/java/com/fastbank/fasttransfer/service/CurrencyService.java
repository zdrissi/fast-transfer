package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public boolean isValidCurrency(String currency) {
        return currencyRepository.existsByCode(currency);
    }
}
