package com.fastbank.fasttransfer.service;

import com.fastbank.fasttransfer.api.domain.TransferFundsRequest;
import com.fastbank.fasttransfer.api.domain.TransferFundsResponse;
import com.fastbank.fasttransfer.client.CurrencyExchangeClientCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private CurrencyExchangeClientCaller currencyExchangeClientCaller;

    public TransferFundsResponse transferFunds(TransferFundsRequest transferFundsRequest) {
        return null;
    }
}
