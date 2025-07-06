package com.fastbank.fasttransfer.client;

import com.fastbank.fasttransfer.base.AbstractComponentTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CurrencyExchangeClientCallerTest extends AbstractComponentTest {

    @Mock
    private CurrencyExchangeClient currencyExchangeClient;

    @InjectMocks
    private CurrencyExchangeClientCaller currencyExchangeClientCaller;

    @Test
    void testGetExchangeRate() {
        // Arrange
        when(currencyExchangeClient.getExchangeRates(null, CURRENCY, List.of(OTHER_CURRENCY)))
                .thenReturn(CurrencyExchangeResponse.builder()
                        .data(Map.of(OTHER_CURRENCY, BigDecimal.valueOf(1000)))
                        .build());

        // Act
        BigDecimal exchangeRate = currencyExchangeClientCaller.getExchangeRate(CURRENCY, OTHER_CURRENCY);

        // Assert
        assertEquals(BigDecimal.valueOf(1000), exchangeRate);
    }
}
