package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsResponse;
import com.fastbank.fasttransfer.base.AbstractComponentTest;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import com.fastbank.fasttransfer.exception.InsufficientFundsException;
import com.fastbank.fasttransfer.repository.BankAccountRepository;
import com.fastbank.fasttransfer.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransferServiceTest extends AbstractComponentTest {

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
        when(bankAccountRepository.findByIbanIn(List.of(DEBIT_ACCOUNT_NUMBER, CREDIT_ACCOUNT_NUMBER)))
                .thenReturn(List.of(createBankAccount(CURRENCY, DEBIT_ACCOUNT_NUMBER), createBankAccount(CURRENCY, CREDIT_ACCOUNT_NUMBER)));

        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .creditAccountNumber(CREDIT_ACCOUNT_NUMBER)
                .debitAccountNumber(DEBIT_ACCOUNT_NUMBER)
                .amount(BigDecimal.valueOf(1000));

        // Act & Assert
        assertThrows(InsufficientFundsException.class,
                () -> transferService.transferFunds(transferFundsRequest));
    }

    @ParameterizedTest
    @CsvSource({"EUR,EUR,20", "EUR,USD,21.80"})
    void givenEnoughFunds_whenTransferFunds_thenOk(String debitCurrency, String creditCurrency, BigDecimal creditAccountBalance) {

        // Arrange
        BankAccountEntity debitAccount = createBankAccount(debitCurrency, DEBIT_ACCOUNT_NUMBER);
        BankAccountEntity creditAccount = createBankAccount(creditCurrency, CREDIT_ACCOUNT_NUMBER);
        when(bankAccountRepository.findByIbanIn(List.of(DEBIT_ACCOUNT_NUMBER, CREDIT_ACCOUNT_NUMBER)))
                .thenReturn(List.of(debitAccount, creditAccount));

        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .creditAccountNumber(CREDIT_ACCOUNT_NUMBER)
                .debitAccountNumber(DEBIT_ACCOUNT_NUMBER)
                .amount(BigDecimal.TEN);

        // If currencies are different the mock exchange rate
        if (!debitCurrency.equals(creditCurrency)) {
            when(currencyExchangeClientCaller.getExchangeRate(CURRENCY, OTHER_CURRENCY))
                    .thenReturn(BigDecimal.valueOf(1.18));
        }

        // Act
        TransferFundsResponse result = transferService.transferFunds(transferFundsRequest);

        // Assert
        assertNotNull(result.getTransactionId());
        assertEquals(BigDecimal.ZERO, debitAccount.getBalance());
        assertEquals(creditAccountBalance, creditAccount.getBalance());
    }

    /**
     * Creates a bank account with a balance of 10
     *
     * @param currency account currency
     * @return bank account entity
     */
    private BankAccountEntity createBankAccount(String currency, String iban) {
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setIban(iban);
        bankAccount.setBalance(BigDecimal.TEN);
        bankAccount.setCurrency(currency);
        return bankAccount;
    }
}
