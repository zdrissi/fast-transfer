package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.base.AbstractComponentTest;
import com.fastbank.fasttransfer.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CurrencyServiceTest extends AbstractComponentTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void givenValidCurrency_whenIsValidCurrency_thenOk() {
        // Arrange
        when(currencyRepository.existsByCode(any()))
                .thenReturn(true);

        // Act
        boolean result = currencyService.isValidCurrency(CURRENCY);

        // Assert
        assertTrue(result);
    }
}
