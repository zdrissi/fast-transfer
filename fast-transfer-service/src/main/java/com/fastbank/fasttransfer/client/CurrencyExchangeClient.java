package com.fastbank.fasttransfer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Currency exchange feign client
 */
@FeignClient(name = "currencyExchangeClient", url = "${app.currency-exchange-api.url}")
public interface CurrencyExchangeClient {

    @GetMapping("/v1/latest")
    CurrencyExchangeResponse getExchangeRates(@RequestParam("apikey") String apiKey, @RequestParam("base_currency") String baseCurrency, @RequestParam("currencies") List<String> currencies);
}
