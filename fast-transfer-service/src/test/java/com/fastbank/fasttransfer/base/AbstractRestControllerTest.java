package com.fastbank.fasttransfer.base;

import com.fastbank.fasttransfer.service.BankAccountService;
import com.fastbank.fasttransfer.service.CurrencyService;
import com.fastbank.fasttransfer.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public abstract class AbstractRestControllerTest {

    protected static final String OWNER_ID = "Q266201";

    protected static final String OWNER_NAME = "J. Doe";

    protected static final String CURRENCY = "EUR";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected CurrencyService currencyService;

    @MockitoBean
    protected BankAccountService bankAccountService;

    @MockitoBean
    protected TransferService transferService;

    @BeforeEach
    public void init() {
        when(currencyService.isValidCurrency(any())).thenReturn(true);
        when(bankAccountService.exists(any())).thenReturn(true);
    }
}
