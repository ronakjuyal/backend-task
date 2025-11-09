# 🏎️ HotWheels Backend Task — Dealer & Vehicle Management API

## 📘 Overview
This project is a **Spring Boot 3** REST API backend that manages **Dealers**, **Vehicles**, and **Subscription Payments** with simulated payment processing and **JWT-based authentication**.  
It includes Swagger UI for testing, a 5-second simulated payment callback, and standardized JSON responses.

---

## ⚙️ Tech Stack
| Layer | Technology |
|--------|-------------|
| **Backend Framework** | Spring Boot 3 |
| **Database** | PostgreSQL (JPA/Hibernate) |
| **Authentication** | JWT (JSON Web Token) |
| **Documentation** | Swagger / OpenAPI 3 |
| **Mapping** | MapStruct |
| **Build Tool** | Maven |
| **Language** | Java 17 |

---

## 🧱 Features
- Dealer Management (CRUD)  
- Vehicle Management (CRUD + Premium Dealer Filter)  
- Payment Simulation (auto updates after 5 seconds)  
- JWT Authentication & Authorization  
- Centralized API Response Wrapper  
- Integrated Swagger UI  
- PostgreSQL persistence

---

## 🚀 Setup Instructions

### 1️⃣ Prerequisites
- Java 17+  
- Maven 3.8+  
- PostgreSQL running locally (default port `5432`)

### 2️⃣ Create Database
CREATE DATABASE hotwheels_db;

### 3️⃣ Configure DB in `application.properties`
spring.datasource.url=jdbc:postgresql://localhost:5432/hotwheels_db  
spring.datasource.username=postgres  
spring.datasource.password=postgres  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

### 4️⃣ Build & Run
mvn clean package  
mvn spring-boot:run

Swagger UI: http://localhost:8080/swagger-ui.html

---

## 🔐 Authentication (JWT)

### Login Endpoint
POST /api/auth/login

Request Body
{
  "username": "admin",
  "password": "password"
}

Response
{
  "success": true,
  "data": {
    "token": "<your-jwt-token>"
  },
  "message": "Login successful"
}

Use this token in subsequent API calls:  
Authorization: Bearer <token>

---

## 📘 API Endpoints

### 🧍 Dealer Management
Method / Endpoint / Description / Auth  
POST /api/dealers — Create dealer — ✅  
GET /api/dealers — Get all dealers — ✅  
GET /api/dealers/{id} — Get dealer by ID — ✅  
PUT /api/dealers/{id} — Update dealer — ✅  
DELETE /api/dealers/{id} — Delete dealer — ✅

Dealer Example
{
  "id": 1,
  "name": "SuperCars",
  "email": "supercars@example.com",
  "subscriptionType": "PREMIUM"
}

---

### 🚗 Vehicle Management
POST /api/vehicles — Add a vehicle — ✅  
GET /api/vehicles — List all vehicles — ✅  
GET /api/vehicles/{id} — Get vehicle by ID — ✅  
PUT /api/vehicles/{id} — Update vehicle — ✅  
DELETE /api/vehicles/{id} — Delete vehicle — ✅  
GET /api/vehicles/premium-dealers — Get vehicles of PREMIUM dealers — ✅

Vehicle Example
{
  "id": 10,
  "dealerId": 1,
  "model": "Ferrari F8",
  "price": 320000.0,
  "status": "AVAILABLE"
}

---

### 💳 Payment Simulation
POST /api/payment/initiate — Initiate payment (auto success after 5s) — ❌ (public)  
GET /api/payment/{id} — Get payment status by ID — ✅

Initiate Request
{
  "dealerId": 1,
  "amount": 100.0,
  "method": "UPI"
}

Response (initial)
{
  "success": true,
  "data": {
    "id": 1,
    "dealerId": 1,
    "amount": 100.0,
    "method": "UPI",
    "status": "PENDING"
  },
  "message": "Payment initiated"
}

After 5 seconds (auto update):
{
  "id": 1,
  "dealerId": 1,
  "amount": 100.0,
  "method": "UPI",
  "status": "SUCCESS"
}

---

### 🔑 Auth Endpoints
POST /api/auth/login — Authenticate user & get JWT token — ❌ (public)

---

## 🧰 Example API Response Structure
{
  "success": true,
  "data": { },
  "message": "Operation successful",
  "timestamp": 1699999999999
}

---

## 🧭 Swagger UI (Interactive Docs)
Access the full API documentation at:  
http://localhost:8080/swagger-ui.html

### 💡 JWT in Swagger
1. Login via /api/auth/login  
2. Copy token (without “Bearer ”)  
3. Click **Authorize** on Swagger  
4. Paste token → **Authorize**  
5. Test secured endpoints

---

## 🐳 Docker (Optional)
Dockerfile:
FROM eclipse-temurin:17-jdk-jammy  
ARG JAR_FILE=target/*.jar  
COPY ${JAR_FILE} app.jar  
ENTRYPOINT ["java","-jar","/app.jar"]

Build & Run:
docker build -t hotwheels-backend .  
docker run -p 8080:8080 hotwheels-backend

---

## 👨‍💻 Default Credentials
Username: admin  
Password: password  
Role: ADMIN

---

## 🧾 License
This project is for educational and demonstration purposes.
