package com.fastbank.fasttransfer.client;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyExchangeResponse {
    private Map<String, BigDecimal> data;
}
