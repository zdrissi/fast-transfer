package com.fastbank.fasttransfer.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyExchangeClientCaller {

    private final CurrencyExchangeClient currencyExchangeClient;

    @Value("${currency-exchange-api.key}")
    private String apiKey;

    @CircuitBreaker(name = "exchangeRatesApi")
    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        return currencyExchangeClient.getExchangeRates(apiKey, baseCurrency, List.of(targetCurrency))
                .getData()
                .get(targetCurrency);
    }
}
