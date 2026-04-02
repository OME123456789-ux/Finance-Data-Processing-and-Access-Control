# Finance Dashboard Backend (Spring Boot)

JWT authentication + role-based access control (ADMIN, ANALYST, VIEWER) for a finance dashboard.

## Requirements

- Java 17+
- MySQL 8+
- Maven

## Database

Create a MySQL database named:

`finance_dashboard`

The app uses Hibernate to create/update tables (`spring.jpa.hibernate.ddl-auto=update`).

## Configuration

Update environment variables (or edit `application.properties`) before running:

- `DB_URL` (default: `jdbc:mysql://localhost:3306/finance_dashboard?...`)
- `DB_USERNAME` (default: `root`)
- `DB_PASSWORD` (default: `password`)
- `JWT_SECRET` (required for production; default provided for dev)

## Run

From the project root:

`mvn spring-boot:run`

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

OpenAPI docs:

- `http://localhost:8080/v3/api-docs`

## Role permissions (enforced with `@PreAuthorize`)

- `ADMIN`: can create/update/delete financial records; can access all dashboard endpoints.
- `ANALYST`: can read records + access dashboard totals, category totals, and monthly summary.
- `VIEWER`: can read records + dashboard totals only.

Note: all record/dashboard queries are scoped to the authenticated user (you only see your own `FinancialRecord`s).

## Endpoints

Auth:
- `POST /auth/signup` (public) -> creates a `VIEWER` user
- `POST /auth/login` (public) -> returns a JWT token

Financial records (scoped to your user):
- `POST /records` (ADMIN only)
- `GET /records` (all roles) with optional filters: `date`, `category`, `type` + pagination (`page`, `size`)
- `PUT /records/{id}` (ADMIN only)
- `DELETE /records/{id}` (ADMIN only)

Dashboard:
- `GET /dashboard/totals` (all roles)
- `GET /dashboard/categories` (ADMIN/ANALYST)
- `GET /dashboard/monthly` (ADMIN/ANALYST)

## Postman

A ready-to-use Postman collection is included:

`docs/postman/finance-dashboard.postman_collection.json`

Use `Authorization: Bearer <token>` for all requests except `/auth/signup` and `/auth/login`.

