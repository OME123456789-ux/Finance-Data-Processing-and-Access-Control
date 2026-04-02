# Finance Dashboard Backend - Assignment Submission

Spring Boot backend for a Finance Dashboard system with JWT authentication, role-based access control, financial record management, filtering, and dashboard analytics.

## Tech Stack

- Java (developed with JDK 25, compiled with Java 17 target compatibility)
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- Spring Security
- MySQL
- JWT (JJWT)
- Lombok
- Bean Validation

## Assignment Features Implemented

- Signup and login APIs with JWT token generation
- BCrypt password encryption
- Stateless JWT authentication filter
- Role-based access control (`ADMIN`, `ANALYST`, `VIEWER`)
- Financial records CRUD APIs
- Record filtering by `date`, `category`, and `type`
- Pagination support for listing records
- Dashboard APIs:
  - Total income
  - Total expenses
  - Net balance
  - Category-wise totals
  - Monthly summary
- Global exception handling with proper HTTP status codes
- Layered architecture: `controller`, `service`, `repository`, `entity`, `dto`, `security`, `exception`

## Data Access Model

This project is configured in **shared data mode** as requested:

- All roles access the same financial dataset.
- Data is **not** filtered by `user_id` during records listing/dashboard analytics.
- Role restrictions control what actions each role can perform.

## Role Permissions

- `ADMIN`
  - `POST /records`
  - `GET /records`
  - `PUT /records/{id}`
  - `DELETE /records/{id}`
  - `GET /dashboard/totals`
  - `GET /dashboard/categories`
  - `GET /dashboard/monthly`

- `ANALYST`
  - `GET /records`
  - `GET /dashboard/totals`
  - `GET /dashboard/categories`
  - `GET /dashboard/monthly`

- `VIEWER`
  - `GET /records`
  - Cannot create/update/delete records
  - Cannot access dashboard analytics endpoints in current role policy

## Database Tables

### Required
1. `users`
   - `id`, `name`, `email`, `password`, `role`, `status`
2. `financial_records`
   - `id`, `amount`, `type`, `category`, `date`, `notes`, `user_id` (optional in shared mode)

## API Endpoints

### Public
- `POST /auth/signup`
- `POST /auth/login`

### Protected
- `POST /records` (ADMIN)
- `GET /records` (ADMIN/ANALYST/VIEWER)
- `PUT /records/{id}` (ADMIN)
- `DELETE /records/{id}` (ADMIN)
- `GET /dashboard/totals` (ADMIN/ANALYST)
- `GET /dashboard/categories` (ADMIN/ANALYST)
- `GET /dashboard/monthly` (ADMIN/ANALYST)

## Request Samples

### Signup
`POST /auth/signup`
```json
{
  "name": "Admin User",
  "email": "admin@example.com",
  "password": "Admin@123",
  "role": "ADMIN"
}
```

### Login
`POST /auth/login`
```json
{
  "email": "admin@example.com",
  "password": "Admin@123"
}
```

### Create Financial Record (ADMIN)
`POST /records`
```json
{
  "amount": 2500.00,
  "type": "EXPENSE",
  "category": "Food",
  "date": "2026-04-02",
  "notes": "Lunch"
}
```

Use header for protected APIs:

`Authorization: Bearer <JWT_TOKEN>`

## Setup and Run

## Prerequisites
- JDK 25 (local development)
- Maven
- MySQL 8+

### Java Compatibility Note
- Development runtime used: `JDK 25`
- Project build target: `Java 17` (configured in `pom.xml`)
- This keeps the assignment project stable and compatible across environments.

### 1) Create Database
Create MySQL database:

`finance_dashboard`

### 2) Configure `application.properties`
Set values (or environment variables):

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `app.security.jwt.secret`
- `app.security.jwt.expiration-ms`

### 3) Run Application
From project root:

`mvn spring-boot:run`

## API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Postman Collection

`docs/postman/finance-dashboard.postman_collection.json`

## Notes

- JWT authentication is stateless (`SessionCreationPolicy.STATELESS`).
- CSRF is disabled for API usage.
- Role checks are enforced using `@PreAuthorize` with consistent `ROLE_` authority mapping.

