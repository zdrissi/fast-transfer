package com.fastbank.fasttransfer.validator;

import com.fastbank.fasttransfer.base.AbstractComponentTest;
import com.fastbank.fasttransfer.service.BankAccountService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class BankAccountValidatorTest extends AbstractComponentTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountValidator validator;

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void testBankAccountValidity(boolean accountExists) {

        // Arrange
        when(bankAccountService.exists(any()))
                .thenReturn(accountExists);

        // Act
        boolean valid = validator.isValid(UUID.randomUUID().toString(), null);

        // Assert
        assertEquals(accountExists, valid);
    }
}
