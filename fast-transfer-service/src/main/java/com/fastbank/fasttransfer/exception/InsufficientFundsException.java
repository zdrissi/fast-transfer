package com.fastbank.fasttransfer.exception;

/**
 * Exception thrown when funds to transfer are insufficient
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Debit account does not have sufficient funds");
    }
}
