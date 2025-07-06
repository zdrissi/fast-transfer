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


* **Rule #2: Currencies used in bank account creation and in transfers must be supported.**

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

| Method | URL                           | Description                                   | Request Body | Path Variables |
|--------|-------------------------------|-----------------------------------------------|--------------|----------------|
| POST   | `/v1/account`                 | Create a new account                          | Yes          | No             |
| GET    | `/v1/account/{accountNumber}` | Get a bank account infos                      | No           | Yes            |
| POST   |  `/v1/account/transfer-funds` | Perform a fund transfer between two accounts  | Yes          | No             |

### Open Api (Swagger)

```
http://localhost:3112/swagger-ui/index.html
```

---

## 🛠️ Technologies Used

* **Language & Frameworks**:

    * Java 21
    * Spring Boot 3.+
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
    * TestContainers
    * Integration Testing
    * JaCoCo (Test Coverage Reports)

* **DevOps**:

    * Docker
    * Docker Compose
    * SonarQube

### Prerequisites

#### Define Variables in .env file

Create a `.env` file in the project's root directory:

```
CURRENCY_EXCHANGE_API_KEY={API_KEY}
POSTGRES_USER={POSTGRES_USER}
POSTGRES_PASSWORD={POSTGRES_PASSWORD}
```

### Building and running project

Start postgres service using docker-compose.

### IDE Run

Run the `FastTransferApplication`'s `main()` class.

### Maven Run
To build and run the application with `Maven`, please follow the directions shown below;

```sh
$ mvn clean install
$ cd fast-transfer-service
$ mvn spring-boot:run
```

---

### Docker Run
The application can be built and run by the `Docker` engine.

```sh
$ mvn clean install
$ docker-compose up --build
```

### Sonarqube

- Start sonarqube service using `docker-compose up sonarqube` and go to `localhost:9000`
- Enter username and password as `admin`
- Change password
- Click `Create Local Project`
- Choose the baseline for this code for the project as `Use the global setting`
- Click `Locally` in Analyze Method
- Define Token
- Click `Continue`
- Copy `sonar.host.url` and `sonar.token` (`sonar.login`) in the `properties` part in  `pom.xml`
- Run `mvn sonar:sonar` on **fast-transfer-service** to show code analysis