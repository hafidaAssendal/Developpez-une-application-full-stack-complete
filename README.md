# MDD — Monde de Dév

> Social network dedicated to developers — MVP version

---

## Table of contents

- [Overview](#overview)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
- [Environment variables](#environment-variables)
- [Database](#database)
- [API endpoints](#api-endpoints)
- [Security](#security)
---

## Overview

MDD (Monde de Dév) is a social network built for developers. It aims to foster connections and collaboration between peers who share common programming interests.

### MVP features

- User registration and login (email or username)
- Subscribe to programming topics (like: JavaScript, Java, Python, Angular...)
- Personalized article feed sorted chronologically
- Create and read articles
- Add comments on articles
- User profile management
- Fully responsive (mobile, tablet, desktop....)

---

## Tech stack

### Backend

| Technology | Version | Role |
|---|---|---|
| Java | 11 | Language |
| Spring Boot | 2.7.3 | Framework |
| Spring Security | 5.7.3 | Authentication |
| Spring Data JPA | 2.7.2 | ORM |
| Hibernate | 5.6.10 | Database mapping |
| JWT (jjwt) | 0.11.5 | Token authentication |
| MapStruct | 1.5.3 | DTO mapping |
| Lombok | 1.18.24 | Boilerplate reduction |
| MySQL | 8.0 | Database |
| Maven | 3.x | Build tool |

### Frontend

| Technology | Version | Role |
|---|---|---|
| Angular | 17 | Framework |
| TypeScript | 5.2 | Language |
| Angular Material | 17 | UI Components |
| RxJS | 7.8 | Reactive programming |
| Angular CLI | 17 | Tooling |

### Infrastructure

| Technology | Role |
|---|---|
| Docker | MySQL container |
| phpMyAdmin | Database UI |

---

## Project structure

```
MDD/
├── .gitignore
├── README.md
│
├── back/
│   ├── .env                    ← sensitive data (create this file)
│   ├── .env.example            ← template of .env file
│   ├── .gitignore
│   ├── docker-compose.yml
│   ├── pom.xml
│   └── src/main/java/com/openclassrooms/mddapi/
│       ├── controllers/
│       │   ├── AuthController.java
│       │   ├── UserController.java
│       │   ├── ArticleController.java
│       │   ├── ThemeController.java
│       │   └── SubscriptionController.java
│       ├── exception/
│       │   ├── BadRequestException.java
│       │   ├── NotFoundException.java
│       │   ├── UnauthorizedException.java
│       │   └── GlobalExceptionHandler.java
│       ├── mapper/
│       │   ├── EntityMapper.java
│       │   ├── UserMapper.java
│       │   ├── UpdateUserMapper.java
│       │   ├── ArticleMapper.java
│       │   ├── ThemeMapper.java
│       │   └── CommentMapper.java
│       ├── models/
│       │   ├── User.java
│       │   ├── Article.java
│       │   ├── Theme.java
│       │   ├── Comment.java
│       │   └── Subscription.java
│       ├── payload/
│       │   ├── request/
│       │   │   ├── LoginRequest.java
│       │   │   ├── SignupRequest.java
│       │   │   ├── ArticleRequest.java
│       │   │   ├── CommentRequest.java
│       │   │   └── UpdateUserRequest.java
│       │   └── response/
│       │       ├── JwtResponse.java
│       │       ├── MessageResponse.java
│       │       ├── UserResponse.java
│       │       ├── UpdateUserResponse.java
│       │       ├── ArticleResponse.java
│       │       ├── CommentResponse.java
│       │       └── ThemeResponse.java
│       ├── repository/
│       │   ├── UserRepository.java
│       │   ├── ArticleRepository.java
│       │   ├── ThemeRepository.java
│       │   ├── CommentRepository.java
│       │   └── SubscriptionRepository.java
│       ├── security/
│       │   ├── jwt/
│       │   │   ├── AuthEntryPointJwt.java
│       │   │   ├── AuthTokenFilter.java
│       │   │   └── JwtUtils.java
│       │   ├── services/
│       │   │   ├── UserDetailsImpl.java
│       │   │   └── UserDetailsServiceImpl.java
│       │   └── WebSecurityConfig.java
│       └── services/
│           ├── UserService.java
│           ├── ArticleService.java
│           ├── ThemeService.java
│           └── SubscriptionService.java
│
└── front/
    └── src/app/
        ├── pages/
        │   ├── auth/
        │   │   ├── components/
        │   │   │   ├── login/
        │   │   │   └── register/
        │   │   ├── interfaces/
        │   │   │   ├── login-request.interface.ts
        │   │   │   ├── signup-request.interface.ts
        │   │   │   └── jwt-response.interface.ts
        │   │   └── services/
        │   │       └── auth.service.ts
        │   │
        │   ├── features/
        │   │   ├── articles/
        │   │   │      ├── list/
        │   │   │      ├── detail-article/
        │   │   │      └── create-article/
        │   │   │   
        │   │   ├── themes/
        │   │   │     └── theme/
        │   │   │
        │   │   └── profile/
        │   │
        │   └── home/
        │
        ├── shared/
        │   ├── header/
        │   ├── back-button/
        │   └── not-found/
        │
        ├── guards/
        │   ├── auth.guard.ts
        │   └── guest.guard.ts
        │
        ├── interceptors/
        │   └── jwt.interceptor.ts
        │
        ├── interfaces/
        │   ├── article-request.interface.ts
        │   ├── article.interface.ts
        │   ├── comment-request.interface.ts
        │   ├── comment.interface.ts
        │   ├── messageResponse.interface.ts
        │   ├── theme.interface.ts
        │   ├── update-profile-request.interface.ts
        │   ├── update-profile-response.interface.ts
        │   └── user.interface.ts
        │
        ├── services/
        │   ├── token.service.ts
        │   ├── article.service.ts
        │   ├── theme.service.ts
        │   └── user-service.service.ts
        │
        ├── app.component.ts
        ├── app.component.html
        ├── app.component.scss
        ├── app.config.ts
        └── app.routes.ts

```

---

## Prerequisites

| Tool | Minimum version |
|---|---|
| Java | 11+ |
| Maven | 3.x |
| Node.js | 18+ |
| Angular CLI | 17 |
| Docker Desktop | latest |
| Git | latest |

---

## Getting started

### 1. Clone the repository

```bash
git clone https://github.com/hafidaAssendal/Developpez-une-application-full-stack-complete.git
cd Developpez-une-application-full-stack-complete
```

### 2. Set up environment variables

```bash
cd back
cp .env.example .env
# Fill in your values in .env
```

### 3. Start the database

```bash
cd back
docker-compose up -d

# Verify containers are running
docker ps
# mysql-db   → port 3307
# phpmyadmin → port 8081
```

### 4. Install dependencies:

```bash
  mvn clean install
```

### 5. Start the backend

```bash
cd back
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Backend runs on `http://localhost:8080`

### 6. Start the frontend

```bash
cd front
npm install
ng serve
```

Frontend runs on `http://localhost:4200`

---

## Environment variables

Create a `.env` file in `back/` based on `.env.example`:

```properties
# Database
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_DATABASE=mdd_db
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_very_long_secret_key_minimum_64_characters_for_HS512
JWT_EXPIRATION=86400000
```

### `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/${MYSQL_DATABASE}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
mdd.app.jwtSecret=${JWT_SECRET}
mdd.app.jwtExpirationMs=${JWT_EXPIRATION}
```

### `application-local.properties`

```properties
spring.config.import=optional:file:../.env[.properties]
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=DEBUG
```

---

## Database

### Access phpMyAdmin

```
URL      : http://localhost:8081
Username : your DB_USERNAME
Password : your DB_PASSWORD
```

### Data model

```
User ──────< Article      (1 user writes many articles)
User ──────< Comment      (1 user writes many comments)
User ──────< Subscription (1 user has many subscriptions)
Theme ─────< Article      (1 theme has many articles)
Theme ─────< Subscription (1 theme has many subscribers)
Article ───< Comment      (1 article has many comments)
```

### Seed data — themes

```sql
INSERT INTO themes (title, description) VALUES
('JavaScript', 'Essential web programming language used client and server side.'),
('Java', 'Robust object-oriented language widely used in enterprise backends.'),
('Python', 'Versatile language ideal for data science and AI.'),
('Angular', 'TypeScript frontend framework developed by Google.');
```

---

## API endpoints

### Authentication (public)

| Method | Endpoint | Status | Description |
|---|---|---|---|
| POST | `/api/auth/register` | 201 | Register new user |
| POST | `/api/auth/login` | 200 | Login → JWT token |

### User (authenticated)

| Method | Endpoint | Status | Description |
|---|---|---|---|
| GET | `/api/user/me` | 200 | Get profile |
| PATCH | `/api/user/me` | 200 | Update profile |

### Articles (authenticated)

| Method | Endpoint | Status | Description |
|---|---|---|---|
| GET | `/api/articles` | 200 | Get feed |
| GET | `/api/articles/{id}` | 200 | Get article by id |
| POST | `/api/articles` | 201 | Create article |
| GET | `/api/articles/{id}/comments` | 200 | Get comments |
| POST | `/api/articles/{id}/comments` | 201 | Add comment |

### Themes (authenticated)

| Method | Endpoint | Status | Description |
|---|---|---|---|
| GET | `/api/themes` | 200 | Get all themes |

### Subscriptions (authenticated)

| Method | Endpoint | Status | Description |
|---|---|---|---|
| POST | `/api/subscriptions/{id}` | 201 | Subscribe to theme |
| DELETE | `/api/subscriptions/{id}` | 204 | Unsubscribe from theme |

### Request examples

**Register:**
```json
POST /api/auth/register
{
  "username": "john",
  "email": "john@test.fr",
  "password": "Test2026*"
}
```

**Login:**
```json
POST /api/auth/login
{
  "identifier": "john@test.fr",
  "password": "Test2026*"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Create article:**
```json
POST /api/articles
Authorization: Bearer <token>
{
  "title": "Introduction to Python",
  "content": "Python is a versatile language...",
  "themeId": 3
}
```

## Security

- **JWT stateless authentication** — token stored client-side
- **BCrypt** — password hashing
- **Spring Security** — route protection
- Public routes: `/api/auth/login`, `/api/auth/register`
- Protected routes: all `/api/**` require a valid JWT token
- Password rules: 8+ characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character
- Sensitive data never returned in API responses (no password in DTOs)
- JWT token regenerated on email change

---

---
##  Author

Hafida Assendal

GitHub: @hafidaAssendal

**Note**: This project was developed as part of the "Full-Stack Developer - Java and Angular" path at OpenClassrooms.
