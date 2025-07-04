package com.fastbank.fasttransfer.service;

import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    public boolean exists(String accountNumber) {
        return true;
    }
}
