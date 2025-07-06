package com.fastbank.fasttransfer.client;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Currency exchange response bean
 */
@Data
@Builder
public class CurrencyExchangeResponse {

    /**
     * Currency, exchange rate map
     */
    private Map<String, BigDecimal> data;
}
