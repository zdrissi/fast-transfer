package com.fastbank.fasttransfer.exception;

/**
 * Exception thrown when funds to transfer are insufficient
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
