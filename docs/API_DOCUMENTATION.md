# Finance Dashboard API Documentation

## Overview

This project is a Spring Boot REST API for a finance dashboard system. It provides:

- User signup and login
- JWT-based authentication
- Role-based access control
- Financial record CRUD
- Record filtering and pagination
- Dashboard analytics

Base URL:

```text
http://localhost:8080
```

OpenAPI and Swagger:

- Swagger UI: `GET /swagger-ui.html`
- OpenAPI JSON: `GET /v3/api-docs`

## Authentication

Protected endpoints require a bearer token in the `Authorization` header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Public endpoints:

- `POST /auth/signup`
- `POST /auth/login`
- `GET /swagger-ui.html`
- `GET /swagger-ui/**`
- `GET /v3/api-docs/**`

## Roles and Access

### ADMIN

- `POST /records`
- `GET /records`
- `PUT /records/{id}`
- `DELETE /records/{id}`
- `GET /dashboard/totals`
- `GET /dashboard/categories`
- `GET /dashboard/monthly`

### ANALYST

- `GET /records`
- `GET /dashboard/totals`
- `GET /dashboard/categories`
- `GET /dashboard/monthly`

### VIEWER

- `GET /records`

## Data Model

### User

```json
{
  "id": 1,
  "name": "Admin User",
  "email": "admin@example.com",
  "role": "ADMIN",
  "status": "ACTIVE"
}
```

### Financial Record

```json
{
  "id": 1,
  "amount": 2500.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Monthly salary"
}
```

### Enums

`UserRole`
- `ADMIN`
- `ANALYST`
- `VIEWER`

`UserStatus`
- `ACTIVE`
- `INACTIVE`

`FinancialRecordType`
- `INCOME`
- `EXPENSE`

## Validation Rules

### Signup

- `name`: required, max `100`
- `email`: required, valid email, max `150`
- `password`: required, min `8`, max `72`
- `role`: required

### Login

- `email`: required, valid email, max `150`
- `password`: required, min `8`, max `72`

### Financial Record Create/Update

- `amount`: required, minimum `0.01`
- `type`: required
- `category`: required, max `120`
- `date`: required, must be today or in the past
- `notes`: optional, max `1024`

## Endpoints

### 1. Signup

`POST /auth/signup`

Creates a new user and returns a JWT token immediately.

Request body:

```json
{
  "name": "Admin User",
  "email": "admin@example.com",
  "password": "Admin@123",
  "role": "ADMIN"
}
```

Successful response: `201 Created`

```json
{
  "token": "<JWT_TOKEN>",
  "tokenType": "Bearer",
  "user": {
    "id": 1,
    "name": "Admin User",
    "email": "admin@example.com",
    "role": "ADMIN",
    "status": "ACTIVE"
  }
}
```

Possible errors:

- `400 Bad Request` for validation errors
- `409 Conflict` if email already exists

### 2. Login

`POST /auth/login`

Authenticates an existing user and returns a JWT token.

Request body:

```json
{
  "email": "admin@example.com",
  "password": "Admin@123"
}
```

Successful response: `200 OK`

```json
{
  "token": "<JWT_TOKEN>",
  "tokenType": "Bearer",
  "user": {
    "id": 1,
    "name": "Admin User",
    "email": "admin@example.com",
    "role": "ADMIN",
    "status": "ACTIVE"
  }
}
```

Possible errors:

- `400 Bad Request` for validation errors
- `401 Unauthorized` for invalid credentials

### 3. Create Financial Record

`POST /records`

Role required: `ADMIN`

Request body:

```json
{
  "amount": 2500.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Monthly salary"
}
```

Successful response: `201 Created`

```json
{
  "id": 1,
  "amount": 2500.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Monthly salary"
}
```

Possible errors:

- `400 Bad Request` for validation or malformed enum values
- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if user is not `ADMIN`

### 4. List Financial Records

`GET /records`

Roles allowed: `ADMIN`, `ANALYST`, `VIEWER`

Query parameters:

- `date` optional, ISO format `YYYY-MM-DD`
- `category` optional, case-insensitive exact match
- `type` optional, `INCOME` or `EXPENSE`
- `page` optional, default `0`
- `size` optional, default `20`
- `sort` optional, default `date,desc`

Example:

```http
GET /records?date=2026-04-01&category=Salary&type=INCOME&page=0&size=20&sort=date,desc
```

Successful response: `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "amount": 2500.00,
      "type": "INCOME",
      "category": "Salary",
      "date": "2026-04-01",
      "notes": "Monthly salary"
    }
  ],
  "pageable": {},
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 20,
  "number": 0,
  "sort": {},
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

Possible errors:

- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if role is not allowed

### 5. Update Financial Record

`PUT /records/{id}`

Role required: `ADMIN`

Path parameters:

- `id`: financial record ID

Request body:

```json
{
  "amount": 2600.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Adjusted amount"
}
```

Successful response: `200 OK`

```json
{
  "id": 1,
  "amount": 2600.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "Adjusted amount"
}
```

Possible errors:

- `400 Bad Request` for validation or malformed enum values
- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if user is not `ADMIN`
- `404 Not Found` if record does not exist

### 6. Delete Financial Record

`DELETE /records/{id}`

Role required: `ADMIN`

Path parameters:

- `id`: financial record ID

Successful response: `204 No Content`

Possible errors:

- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if user is not `ADMIN`
- `404 Not Found` if record does not exist

### 7. Dashboard Totals

`GET /dashboard/totals`

Roles allowed: `ADMIN`, `ANALYST`

Returns overall income, expenses, and net balance across the shared dataset.

Successful response: `200 OK`

```json
{
  "totalIncome": 10000.00,
  "totalExpenses": 3500.00,
  "netBalance": 6500.00
}
```

Possible errors:

- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if role is not allowed

### 8. Dashboard Category Totals

`GET /dashboard/categories`

Roles allowed: `ADMIN`, `ANALYST`

Returns category-wise income and expense totals.

Successful response: `200 OK`

```json
{
  "categories": [
    {
      "category": "Food",
      "incomeTotal": 0.00,
      "expenseTotal": 1200.00,
      "netBalance": -1200.00
    },
    {
      "category": "Salary",
      "incomeTotal": 10000.00,
      "expenseTotal": 0.00,
      "netBalance": 10000.00
    }
  ]
}
```

Possible errors:

- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if role is not allowed

### 9. Dashboard Monthly Summary

`GET /dashboard/monthly`

Roles allowed: `ADMIN`, `ANALYST`

Returns monthly income and expense totals grouped by year and month.

Successful response: `200 OK`

```json
{
  "monthly": [
    {
      "year": 2026,
      "month": 3,
      "incomeTotal": 10000.00,
      "expenseTotal": 3000.00,
      "netBalance": 7000.00
    },
    {
      "year": 2026,
      "month": 4,
      "incomeTotal": 12000.00,
      "expenseTotal": 3500.00,
      "netBalance": 8500.00
    }
  ]
}
```

Possible errors:

- `401 Unauthorized` if token is missing or invalid
- `403 Forbidden` if role is not allowed

## Error Format

Most application errors are returned in this format:

```json
{
  "timestamp": "2026-04-02T13:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "category: must not be blank",
  "path": "/records"
}
```

Security entry-point responses for unauthenticated and forbidden requests are also JSON and follow a similar structure:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Unauthorized",
  "path": "/records"
}
```

Common status codes:

- `200 OK`
- `201 Created`
- `204 No Content`
- `400 Bad Request`
- `401 Unauthorized`
- `403 Forbidden`
- `404 Not Found`
- `409 Conflict`
- `500 Internal Server Error`

## Notes on Data Access

This project currently runs in shared-data mode:

- all allowed users see the same financial records
- dashboard analytics are computed across the full shared dataset
- records are not filtered by the authenticated `user_id`

Although the `financial_records` table includes an optional `user_id` relation, it is not currently used for row-level access control.

## Quick Start

1. Create a MySQL database named `finance_dashboard`.
2. Configure database and JWT values in [application.properties](../src/main/resources/application.properties).
3. Start the app with Maven:

```bash
mvn spring-boot:run
```

4. Open Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```
