package com.fastbank.fasttransfer.validator;

import com.fastbank.fasttransfer.api.domain.BankAccount;
import com.fastbank.fasttransfer.service.BankAccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidator implements ConstraintValidator<BankAccount, String> {

    private BankAccountService bankAccountService;

    @Override
    public boolean isValid(String bankAccount, ConstraintValidatorContext constraintValidatorContext) {
        return bankAccountService.exists(bankAccount);
    }

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
}
