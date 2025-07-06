package com.fastbank.fasttransfer.validator;

import com.fastbank.fasttransfer.api.domain.Currency;
import com.fastbank.fasttransfer.service.CurrencyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator that checks that currency is supported
 */
@Component
public class CurrencyValidator implements ConstraintValidator<Currency, String> {

    private CurrencyService currencyService;

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        return currencyService.isValidCurrency(currency);
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
