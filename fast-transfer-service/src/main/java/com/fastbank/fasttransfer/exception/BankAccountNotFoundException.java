package com.fastbank.fasttransfer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BankAccountNotFoundException extends RuntimeException {

    private final String bankAccountNumber;
}
