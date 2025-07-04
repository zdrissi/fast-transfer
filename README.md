# 🌐 FastTransfer App

## 📘 Description

**FastTransfer** is a modern online platform that enables seamless, fast, and secure fund transfers between accounts in different currencies. With a scalable and robust architecture, the app is accessible through both a web portal and a mobile application.

### 🔑 Key Features

* **Online Account Creation**
  Open an account entirely online—no need to visit a physical branch.

* **Instant Money Transfers**
  Transfer funds quickly and reliably between accounts.

* **Multi-Currency Support**
  Send and receive money in different currencies effortlessly.

---

## ✅ Requirements

* **Security**: No authentication or authorization is required for the initial version.
* **Exchange Rates**: Rates are fetched from an external API: [Free Currency API](https://freecurrencyapi.com/)

---

## 📐 Business Rules

* **Rule #1: A fund transfer must fail if any of the following occur:**

    * Either the debit or credit account does not exist.
    * The exchange rate cannot be retrieved.
    * The debit account has insufficient balance.
    * The transfer amount is less than or equal to 0.
    * One or both currencies involved are not supported.

---

## 🏗️ Architecture Characteristics

* **Auditability**: TBD (To be defined).
* **Concurrency**: The system must handle concurrent requests from multiple users/systems.
* **Data Integrity**: TBD (To be defined).

---

## 🧩 Domain Design

### Account Model

An account must include the following attributes:

* `ownerId` *(numeric)*: Identifier for the account owner.
* `currency` *(string)*: Currency code (e.g., USD, EUR).
* `balance` *(numeric)*: Current balance of the account.

---

## 🔌 REST API Endpoints

| Method | URL                             | Description          | Request Body | Path Variables |
| ------ | ------------------------------- | -------------------- | ------------ | -------------- |
| POST   | `/api/v1/account/createAccount` | Create a new account | Yes          | No             |

*More endpoints to be defined in future iterations.*

---

## 🛠️ Technologies Used

* **Language & Frameworks**:

    * Java 21
    * Spring Boot 3.0
    * RESTful APIs

* **Resilience & Communication**:

    * Resilience4J
    * OpenFeign

* **API Documentation**:

    * OpenAPI (Swagger)

* **Database & Migrations**:

    * PostgreSQL
    * Flyway

* **Mapping & Testing**:

    * MapStruct
    * JUnit 5
    * Mockito
    * Integration Testing
    * JaCoCo (Test Coverage Reports)

* **DevOps & Monitoring**:

    * Docker
    * Docker Compose
    * GitHub Actions (CI/CD)
    * Postman
    * Prometheus
    * Grafana
    * SonarQube
    * Kubernetes

