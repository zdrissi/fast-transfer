package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.api.controller.AccountApi;
import com.fastbank.fasttransfer.api.domain.*;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class AccountController implements AccountApi {

    @Autowired
    private CurrencyExchangeClientCaller currencyExchangeClientCaller;

    @Override
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        return ok(new CreateBankAccountResponse().accountId(UUID.randomUUID().toString()));
    }

    @Override
    public ResponseEntity<BankAccountInfosResponse> findBankAccount(String accountId) {
        BigDecimal exchangeRate = currencyExchangeClientCaller.getExchangeRate("EUR", "USD");
        log.info("Exchange rate: {}", exchangeRate);
        return ok(new BankAccountInfosResponse().accountId(accountId));
    }

    @Override
    public ResponseEntity<TransferFundsResponse> transferFunds(TransferFundsRequest transferFundsRequest) {
        return null;
    }
}
