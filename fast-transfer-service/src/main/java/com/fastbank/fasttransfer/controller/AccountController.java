package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.api.controller.AccountApi;
import com.fastbank.fasttransfer.api.domain.BankAccountInfosResponse;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountResponse;
import com.fastbank.fasttransfer.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Account management controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        log.info("Create bank account request: {}", createBankAccountRequest);
        return ok(bankAccountService.createBankAccount(createBankAccountRequest));
    }

    @Override
    public ResponseEntity<BankAccountInfosResponse> findBankAccount(String accountNumber) {
        log.info("Finding bank account : {}", accountNumber);
        return ok(bankAccountService.findBankAccount(accountNumber));
    }
}
