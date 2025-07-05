package com.fastbank.fasttransfer.validator;

import com.fastbank.fasttransfer.base.AbstractBaseTest;
import com.fastbank.fasttransfer.service.CurrencyService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CurrencyValidatorTest extends AbstractBaseTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyValidator validator;

    @ParameterizedTest
    @CsvSource({"EUR,true", "ZAK,false"})
    void testCurrencyValidity(String currency, boolean currencyExists) {

        // Arrange
        when(currencyService.isValidCurrency(currency))
                .thenReturn(currencyExists);

        // Act
        boolean valid = validator.isValid(currency, null);

        // Assert
        assertEquals(currencyExists, valid);
    }
}
