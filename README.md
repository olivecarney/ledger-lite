# LedgerLite

LedgerLite is a Java Spring Boot/PostgreSQL finance ledger API focused on transaction validation, balance calculation, and testable backend design.

## What it demonstrates

- Stores money in integer cents, not floating-point numbers
- Validates positive transaction amounts at the API and database layers
- Uses Flyway database migrations
- Calculates account balances from immutable transaction records
- Includes JUnit 5 integration tests with Testcontainers
- Runs locally with Docker Compose
- Documents endpoints with Swagger/OpenAPI

## Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- JUnit 5
- Testcontainers
- Docker Compose
- GitHub Actions
- springdoc OpenAPI

## Domain model

LedgerLite models balances from immutable transaction records rather than storing manually editable balance values.

- A user owns one or more accounts.
- An account has a currency and a list of transactions.
- Transactions are either `INCOME` or `EXPENSE`.
- Balances are calculated by summing income and subtracting expenses.
- Amounts are stored as integer cents to avoid floating-point rounding errors.

## Run locally

Start PostgreSQL:

```bash
docker compose up -d
```

Run the API:

```bash
./mvnw spring-boot:run
```

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

Run tests:

```bash
./mvnw test
```

## Core endpoints

```text
POST /users
GET  /users
GET  /users/{id}

POST /accounts
GET  /accounts
GET  /accounts/{id}
GET  /accounts/{id}/balance

POST /categories
GET  /categories

POST /transactions
GET  /transactions?accountId=...
```

## Example flow

Create a user:

```bash
curl -X POST http://localhost:8080/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alex Ledger","email":"alex@example.com"}'
```

Create an account using the returned `userId`:

```bash
curl -X POST http://localhost:8080/accounts \
  -H 'Content-Type: application/json' \
  -d '{"userId":"<user-id>","name":"Main","currency":"GBP"}'
```

Add income:

```bash
curl -X POST http://localhost:8080/transactions \
  -H 'Content-Type: application/json' \
  -d '{"accountId":"<account-id>","type":"INCOME","amountCents":125000,"description":"Salary"}'
```

Example response:

```json
{
  "id": "2c2b0f3b-8d45-48e5-a622-3a0f89cc0a61",
  "accountId": "7a26c3e5-5d52-4e4e-a40c-dc81ce4f32d9",
  "categoryId": null,
  "type": "INCOME",
  "amountCents": 125000,
  "description": "Salary",
  "occurredAt": "2026-06-04T19:30:00Z",
  "createdAt": "2026-06-04T19:30:00Z"
}
```

Add an expense:

```bash
curl -X POST http://localhost:8080/transactions \
  -H 'Content-Type: application/json' \
  -d '{"accountId":"<account-id>","type":"EXPENSE","amountCents":4525,"description":"Groceries"}'
```

Get the balance:

```bash
curl http://localhost:8080/accounts/<account-id>/balance
```

Example response:

```json
{
  "accountId": "7a26c3e5-5d52-4e4e-a40c-dc81ce4f32d9",
  "currency": "GBP",
  "balanceCents": 120475
}
```
