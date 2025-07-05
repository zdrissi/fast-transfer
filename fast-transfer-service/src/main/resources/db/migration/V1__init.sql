CREATE SEQUENCE IF NOT EXISTS bank_account_id_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS currency_id_seq START WITH 1 INCREMENT BY 50;

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