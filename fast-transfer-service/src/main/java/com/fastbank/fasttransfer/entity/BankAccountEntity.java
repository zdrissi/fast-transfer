package com.fastbank.fasttransfer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Bank Account Entity
 */
@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_gen")
    @SequenceGenerator(name = "bank_account_gen", sequenceName = "bank_account_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "owner_id")
    private String ownerId;

    @Size(max = 200)
    @NotNull
    @Column(name = "owner_name")
    private String ownerName;

    @NotNull
    @Column(name = "iban")
    private String iban;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
