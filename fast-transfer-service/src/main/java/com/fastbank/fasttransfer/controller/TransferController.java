package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.api.controller.TransferApi;
import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsResponse;
import com.fastbank.fasttransfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Transfer management controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransferController implements TransferApi {

    private final TransferService transferService;

    @Override
    public ResponseEntity<TransferFundsResponse> transferFunds(TransferFundsRequest transferFundsRequest) {
        log.info("Transferring funds from {} to {}", transferFundsRequest.getDebitAccountNumber(), transferFundsRequest.getCreditAccountNumber());
        return ok(transferService.transferFunds(transferFundsRequest));
    }
}
