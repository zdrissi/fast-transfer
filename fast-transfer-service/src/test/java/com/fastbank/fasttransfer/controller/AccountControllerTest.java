package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.api.domain.BankAccountInfosResponse;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountResponse;
import com.fastbank.fasttransfer.base.AbstractRestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest extends AbstractRestControllerTest {

    @Test
    void givenValidCreateBankAccountRequest_whenCreateBankAccount_thenOk() throws Exception {
        // Arrange
        CreateBankAccountRequest createBankAccountRequest = new CreateBankAccountRequest()
                .ownerId(OWNER_ID)
                .ownerName(OWNER_NAME)
                .initialBalance(BigDecimal.valueOf(1000))
                .currency(CURRENCY);
        String accountNumber = UUID.randomUUID().toString();
        when(bankAccountService.createBankAccount(any()))
                .thenReturn(new CreateBankAccountResponse().accountNumber(accountNumber));

        // Act & Assert
        mockMvc.perform(post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBankAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber")
                        .value(accountNumber));
    }

    @Test
    void givenInvalidCreateBankAccountRequest_whenCreateBankAccount_thenReturnBadRequest() throws Exception {
        // Arrange
        CreateBankAccountRequest createBankAccountRequest = new CreateBankAccountRequest();
        String accountNumber = UUID.randomUUID().toString();
        when(bankAccountService.createBankAccount(any()))
                .thenReturn(new CreateBankAccountResponse().accountNumber(accountNumber));

        // Act & Assert
        mockMvc.perform(post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBankAccountRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidAccountNumber_whenFindBankAccount_thenOk() throws Exception {
        // Arrange
        String accountNumber = UUID.randomUUID().toString();
        when(bankAccountService.findBankAccount(accountNumber))
                .thenReturn(new BankAccountInfosResponse()
                        .accountId(1L)
                        .accountNumber(accountNumber)
                        .ownerId(OWNER_ID)
                        .ownerName(OWNER_NAME)
                        .currency(CURRENCY)
                );

        // Act & Assert
        mockMvc.perform(get("/v1/account/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(jsonPath("$.ownerId").value(OWNER_ID))
                .andExpect(jsonPath("$.ownerName").value(OWNER_NAME))
                .andExpect(jsonPath("$.currency").value(CURRENCY));
    }
}
