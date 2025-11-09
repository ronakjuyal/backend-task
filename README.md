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
```sql
CREATE DATABASE hotwheels_db;
