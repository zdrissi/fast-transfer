package com.fastbank.fasttransfer.mapper;

import com.fastbank.fasttransfer.api.domain.BankAccountInfosResponse;
import com.fastbank.fasttransfer.api.domain.CreateBankAccountRequest;
import com.fastbank.fasttransfer.entity.BankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "iban", expression = "java( java.util.UUID.randomUUID().toString() )")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "ownerName", source = "ownerName")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "balance", source = "initialBalance")
    BankAccountEntity toBankAccount(CreateBankAccountRequest bankAccount);

    @Mapping(target = "accountId", source = "id")
    @Mapping(target = "accountNumber", source = "iban")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "ownerName", source = "ownerName")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "balance", source = "balance")
    BankAccountInfosResponse toBankAccount(BankAccountEntity entity);
}

