package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsResponse;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import com.fastbank.fasttransfer.entity.TransactionEntity;
import com.fastbank.fasttransfer.exception.InsufficientFundsException;
import com.fastbank.fasttransfer.repository.BankAccountRepository;
import com.fastbank.fasttransfer.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service to handle transfers
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private final BankAccountRepository bankAccountRepository;

    private final TransactionRepository transactionRepository;

    private final CurrencyExchangeClientCaller currencyExchangeClientCaller;

    @Transactional
    public TransferFundsResponse transferFunds(TransferFundsRequest transferFundsRequest) {

        BigDecimal transferAmount = transferFundsRequest.getAmount();

        BankAccountEntity debitAccount = bankAccountRepository.findByIban(transferFundsRequest.getDebitAccountNumber());
        BankAccountEntity creditAccount = bankAccountRepository.findByIban(transferFundsRequest.getCreditAccountNumber());

        if (debitAccount.getBalance().compareTo(transferAmount) >= 0) {
            String transactionUniqueId = UUID.randomUUID().toString();
            debitAccount.debit(transferAmount);
            doCreditOperation(creditAccount, transferAmount, debitAccount.getCurrency());

            TransactionEntity transaction = new TransactionEntity();

            transaction.setTransactionUniqueId(transactionUniqueId);
            transaction.setCreditAccount(creditAccount);
            transaction.setDebitAccount(debitAccount);
            transaction.setAmount(transferAmount);

            transactionRepository.save(transaction);

            return new TransferFundsResponse()
                    .transactionId(transactionUniqueId);
        }

        throw new InsufficientFundsException();
    }

    private void doCreditOperation(BankAccountEntity creditAccount, BigDecimal amount, String currency) {
        BigDecimal convertedAmount = amount;
        String targetCurrency = creditAccount.getCurrency();

        if (!StringUtils.equals(targetCurrency, currency)) {
            BigDecimal exchangeRate = currencyExchangeClientCaller.getExchangeRate(currency, targetCurrency);
            convertedAmount = convertedAmount.multiply(exchangeRate);
        }

        creditAccount.credit(convertedAmount);
    }
}
