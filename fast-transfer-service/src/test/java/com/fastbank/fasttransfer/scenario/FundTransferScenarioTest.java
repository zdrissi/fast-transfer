package com.fastbank.fasttransfer.scenario;

import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.base.AbstractIntegrationTest;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import com.fastbank.fasttransfer.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FundTransferScenarioTest extends AbstractIntegrationTest {

    @Autowired
    private BankAccountService bankAccountService;

    @MockitoBean
    private CurrencyExchangeClientCaller currencyExchangeClientCaller;

    @Test
    void givenEnoughFunds_whenTransferFunds_thenAccountCredited() throws Exception {
        // Arrange
        // Create accounts
        String debitAccountNumber = createBankAccount(BigDecimal.valueOf(100), CURRENCY);
        String creditAccountNumber = createBankAccount(BigDecimal.valueOf(100), OTHER_CURRENCY);
        when(currencyExchangeClientCaller.getExchangeRate(CURRENCY, OTHER_CURRENCY))
                .thenReturn(BigDecimal.valueOf(2));

        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .debitAccountNumber(debitAccountNumber)
                .creditAccountNumber(creditAccountNumber)
                .amount(BigDecimal.valueOf(50));

        // Act
        mockMvc.perform(post("/v1/account/transfer-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andExpect(status().isOk());

        // Assert
        assertEquals(BigDecimal.valueOf(50), bankAccountService.findBankAccount(debitAccountNumber).getBalance());
        assertEquals(BigDecimal.valueOf(200), bankAccountService.findBankAccount(creditAccountNumber).getBalance());
    }

    private String createBankAccount(BigDecimal initialBalance, String currency) {
        CreateBankAccountRequest debitAccount = new CreateBankAccountRequest()
                .ownerId(OWNER_ID)
                .ownerName(OWNER_NAME)
                .initialBalance(initialBalance)
                .currency(currency);

        return bankAccountService.createBankAccount(debitAccount).getAccountNumber();
    }
}
