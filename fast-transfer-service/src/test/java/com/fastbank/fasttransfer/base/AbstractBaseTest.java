package com.fastbank.fasttransfer.base;

import java.util.UUID;

/**
 * Base test class holding common test constants
 */
public interface AbstractBaseTest {

    String CREDIT_ACCOUNT_NUMBER = UUID.randomUUID().toString();

    String DEBIT_ACCOUNT_NUMBER = UUID.randomUUID().toString();

    String ACCOUNT_NUMBER = UUID.randomUUID().toString();

    String OWNER_ID = "Q266201";

    String OWNER_NAME = "J. Doe";

    String CURRENCY = "EUR";

    String OTHER_CURRENCY = "USD";
}
