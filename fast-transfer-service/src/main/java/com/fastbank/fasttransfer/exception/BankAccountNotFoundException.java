package com.fastbank.fasttransfer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when the bank account is not found
 */
@Getter
@RequiredArgsConstructor
public class BankAccountNotFoundException extends RuntimeException {

    private final String bankAccountNumber;
}
