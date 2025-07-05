package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.BankAccountInfosResponse;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountResponse;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import com.fastbank.fasttransfer.exception.BankAccountNotFoundException;
import com.fastbank.fasttransfer.mapper.BankAccountMapper;
import com.fastbank.fasttransfer.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountMapper bankAccountMapper;

    private final BankAccountRepository bankAccountRepository;

    public boolean exists(String accountNumber) {
        return bankAccountRepository.existsByIban(accountNumber);
    }

    public CreateBankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest) {
        BankAccountEntity bankAccount = bankAccountMapper.toBankAccount(createBankAccountRequest);
        bankAccountRepository.save(bankAccount);
        return new CreateBankAccountResponse().accountNumber(bankAccount.getIban());
    }

    @Transactional(readOnly = true)
    public BankAccountInfosResponse findBankAccount(String accountNumber) {
        return bankAccountRepository
                .findByIban(accountNumber)
                .map(bankAccountMapper::toBankAccount)
                .orElseThrow(() -> new BankAccountNotFoundException(accountNumber));
    }
}
