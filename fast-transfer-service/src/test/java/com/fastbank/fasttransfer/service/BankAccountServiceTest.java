package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.BankAccountInfosResponse;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountResponse;
import com.fastbank.fasttransfer.base.AbstractComponentTest;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import com.fastbank.fasttransfer.exception.BankAccountNotFoundException;
import com.fastbank.fasttransfer.mapper.BankAccountMapper;
import com.fastbank.fasttransfer.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceTest extends AbstractComponentTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Spy
    private BankAccountMapper bankAccountMapper = Mappers.getMapper(BankAccountMapper.class);

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    void givenValidAccountNumber_whenExists_thenOk() {

        // Arrange
        when(bankAccountRepository.existsByIban(ACCOUNT_NUMBER))
                .thenReturn(true);

        // Act
        boolean exists = bankAccountService.exists(ACCOUNT_NUMBER);

        // Assert
        assertTrue(exists);
    }

    @Test
    void givenValidCreateBankAccountRequest_whenCreateBankAccount_thenOk() {

        // Arrange
        CreateBankAccountRequest creationRequest = new CreateBankAccountRequest()
                .ownerId(OWNER_ID)
                .ownerName(OWNER_NAME)
                .initialBalance(BigDecimal.TEN)
                .currency(CURRENCY);

        // Act
        CreateBankAccountResponse result = bankAccountService.createBankAccount(creationRequest);

        // Assert
        verify(bankAccountRepository).save(any());
        assertNotNull(result.getAccountNumber());
    }

    @Test
    void givenValidAccountNumber_whenFindBankAccount_thenOk() {

        // Arrange
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setIban(ACCOUNT_NUMBER);
        bankAccount.setOwnerName(OWNER_NAME);
        bankAccount.setCurrency(CURRENCY);
        bankAccount.setBalance(BigDecimal.TEN);

        when(bankAccountRepository.findByIban(ACCOUNT_NUMBER))
                .thenReturn(Optional.of(bankAccount));

        // Act
        BankAccountInfosResponse result = bankAccountService.findBankAccount(ACCOUNT_NUMBER);

        // Assert
        assertEquals(ACCOUNT_NUMBER, result.getAccountNumber());
        assertEquals(OWNER_NAME, result.getOwnerName());
        assertEquals(CURRENCY, result.getCurrency());
        assertEquals(BigDecimal.TEN, result.getBalance());
    }

    @Test
    void givenInValidAccountNumber_whenFindBankAccount_thenOk() {
        // Arrange
        when(bankAccountRepository.findByIban(ACCOUNT_NUMBER))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BankAccountNotFoundException.class,
                () -> bankAccountService.findBankAccount(ACCOUNT_NUMBER));
    }
}
