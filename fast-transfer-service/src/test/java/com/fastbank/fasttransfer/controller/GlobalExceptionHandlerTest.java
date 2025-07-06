package com.fastbank.fasttransfer.controller;

import com.fastbank.fasttransfer.base.AbstractComponentTest;
import com.fastbank.fasttransfer.exception.BankAccountNotFoundException;
import com.fastbank.fasttransfer.exception.InsufficientFundsException;
import com.fastbank.fasttransfer.model.CustomError;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest extends AbstractComponentTest {

    @Mock
    private FeignException feignException;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void givenBankAccountNotFoundException_whenHandleBankAccountNotFound_thenRespondWithBadRequest() {

        // Act
        BankAccountNotFoundException ex = new BankAccountNotFoundException(ACCOUNT_NUMBER);

        CustomError expectedError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Bank Account cannot be found : " + ACCOUNT_NUMBER)
                .isSuccess(false)
                .build();

        // Arrange
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleBankAccountNotFound(ex);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        CustomError actualError = (CustomError) responseEntity.getBody();
        checkCustomError(expectedError, actualError);
    }

    @Test
    void givenInsufficientFundsException_whenHandleInsufficientFunds_thenRespondWithBadRequest() {

        // Act
        InsufficientFundsException ex = new InsufficientFundsException();

        CustomError expectedError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Debit account does not have sufficient funds")
                .isSuccess(false)
                .build();

        // Arrange
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleInsufficientFunds(ex);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        CustomError actualError = (CustomError) responseEntity.getBody();
        checkCustomError(expectedError, actualError);
    }

    @Test
    void givenFeignException_whenHandleFeignException_thenRespondServiceUnavailable() {

        // Act
        CustomError expectedError = CustomError.builder()
                .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
                .message("Service Unavailable, please try again later.")
                .isSuccess(false)
                .build();

        // Arrange
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleFeignException(feignException);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        CustomError actualError = (CustomError) responseEntity.getBody();
        checkCustomError(expectedError, actualError);
    }

    @Test
    void givenMethodArgumentNotValidException_whenHandleMethodArgumentNotValid_thenRespondWithBadRequest() {

        // Act
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        FieldError fieldError = new FieldError("objectName", "fieldName", "error message");
        List<ObjectError> objectErrors = Collections.singletonList(fieldError);

        when(bindingResult.getAllErrors()).thenReturn(objectErrors);

        CustomError expectedError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .subErrors(Collections.singletonList(
                        CustomError.CustomSubError.builder()
                                .field("fieldName")
                                .message("error message")
                                .build()))
                .build();

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMethodArgumentNotValid(ex);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        CustomError actualError = (CustomError) responseEntity.getBody();
        checkCustomError(expectedError, actualError);
    }

    private void checkCustomError(CustomError expectedError, CustomError actualError) {
        assertThat(actualError).isNotNull();
        assertThat(actualError.getTime()).isNotNull();
        assertThat(actualError.getIsSuccess()).isEqualTo(expectedError.getIsSuccess());

        if (expectedError.getMessage() != null) {
            assertThat(actualError.getMessage()).isEqualTo(expectedError.getMessage());
        }

        if (expectedError.getSubErrors() != null) {
            assertThat(actualError.getSubErrors()).hasSameSizeAs(expectedError.getSubErrors());
            if (!expectedError.getSubErrors().isEmpty()) {
                assertThat(actualError.getSubErrors().get(0).getMessage()).isEqualTo(expectedError.getSubErrors().get(0).getMessage());
                assertThat(actualError.getSubErrors().get(0).getField()).isEqualTo(expectedError.getSubErrors().get(0).getField());
                assertThat(actualError.getSubErrors().get(0).getValue()).isEqualTo(expectedError.getSubErrors().get(0).getValue());
                assertThat(actualError.getSubErrors().get(0).getType()).isEqualTo(expectedError.getSubErrors().get(0).getType());
            }
        }
    }
}
