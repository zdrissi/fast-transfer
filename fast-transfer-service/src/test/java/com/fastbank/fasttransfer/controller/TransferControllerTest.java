package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsResponse;
import com.fastbank.fasttransfer.base.AbstractRestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest extends AbstractRestControllerTest {

    @Test
    void givenValidTransferFundsRequest_whenTransferFunds_thenOk() throws Exception {
        // Arrange
        String transactionId = UUID.randomUUID().toString();
        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .debitAccountNumber(DEBIT_ACCOUNT_NUMBER)
                .creditAccountNumber(CREDIT_ACCOUNT_NUMBER)
                .amount(BigDecimal.TEN);
        when(transferService.transferFunds(transferFundsRequest))
                .thenReturn(new TransferFundsResponse().transactionId(transactionId));

        // Act & Assert
        mockMvc.perform(post("/v1/account/transfer-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId")
                        .value(transactionId));
    }

    @Test
    void givenInValidTransferFundsRequest_whenTransferFunds_thenBadRequest() throws Exception {
        // Arrange
        TransferFundsRequest transferFundsRequest = new TransferFundsRequest()
                .amount(BigDecimal.TEN);

        // Act & Assert
        mockMvc.perform(post("/v1/account/transfer-funds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferFundsRequest)))
                .andExpect(status().isBadRequest());
    }
}
