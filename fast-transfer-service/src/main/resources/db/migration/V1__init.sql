CREATE SEQUENCE IF NOT EXISTS bank_account_id_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS currency_id_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS transaction_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE bank_account
(
    id         BIGINT       NOT NULL,
    owner_id   VARCHAR(200) NOT NULL,
    owner_name VARCHAR(200) NOT NULL,
    iban       VARCHAR(255) NOT NULL,
    currency   VARCHAR(255) NOT NULL,
    balance    DECIMAL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_bank_account PRIMARY KEY (id)
);

CREATE TABLE currency
(
    id   BIGINT       NOT NULL,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_currency PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id                    BIGINT       NOT NULL,
    amount                DECIMAL      NOT NULL,
    transaction_unique_id VARCHAR(255),
    debit_account_id      BIGINT,
    credit_account_id     BIGINT,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_CREDIT_ACCOUNT FOREIGN KEY (credit_account_id) REFERENCES bank_account (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_DEBIT_ACCOUNT FOREIGN KEY (debit_account_id) REFERENCES bank_account (id);