package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.base.AbstractBaseTest;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import com.fastbank.fasttransfer.exception.InsufficientFundsException;
import com.fastbank.fasttransfer.repository.BankAccountRepository;
import com.fastbank.fasttransfer.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TransferServiceTest extends AbstractBaseTest {

    public static final String CREDIT_ACCOUNT_NUMBER = UUID.randomUUID().toString();
    public static final String DEBIT_ACCOUNT_NUMBER = UUID.randomUUID().toString();

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CurrencyExchangeClientCaller currencyExchangeClientCaller;

    @InjectMocks
    private TransferService transferService;

    @Test
    void givenInsufficientFunds_whenTransferFunds_thenOk() {
        // Arrange
        when(bankAccountRepository.findByIban(CREDIT_ACCOUNT_NUMBER))
                .thenReturn(Optional.of(createBankAccount(BigDecimal.TEN, CURRENCY)));
        when(bankAccountRepository.findByIban(DEBIT_ACCOUNT_NUMBER))
                .thenReturn(Optional.of(createBankAccount(BigDecimal.TEN, CURRENCY)));

        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .creditAccountNumber(CREDIT_ACCOUNT_NUMBER)
                .debitAccountNumber(DEBIT_ACCOUNT_NUMBER)
                .amount(BigDecimal.valueOf(1000))
                .currency(CURRENCY);

        // Act & Assert
        assertThrows(InsufficientFundsException.class,
                () -> transferService.transferFunds(transferFundsRequest));
    }

    private BankAccountEntity createBankAccount(BigDecimal balance, String currency) {
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setBalance(balance);
        bankAccount.setCurrency(currency);
        return bankAccount;
    }
}
