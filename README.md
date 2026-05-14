# 📱 Phone Store - Modern E-Commerce Platform

[![Quarkus](https://img.shields.io/badge/Framework-Quarkus-333333.svg?style=flat&logo=quarkus)](https://quarkus.io/)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg?style=flat&logo=openjdk)](https://openjdk.org/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1.svg?style=flat&logo=mysql)](https://www.mysql.com/)
[![Hibernate](https://img.shields.io/badge/ORM-Hibernate-59666C.svg?style=flat&logo=hibernate)](https://hibernate.org/)

A powerful, high-performance e-commerce platform built with **Quarkus (Supersonic Subatomic Java)**, featuring a responsive frontend and a robust RESTful API. This project demonstrates modern web development practices including reactive services, server-side rendering with Qute, and advanced database management.

---

## 🚀 Key Features

### 🛒 Shopping Experience
- **Responsive Catalog**: Browse products with advanced filtering (category, price, rating).
- **Smart Search**: Find devices and accessories instantly.
- **Persistent Cart & Wishlist**: Save items across sessions using local storage integration.
- **Promotions & Discounts**: Apply promo codes and view active marketing campaigns.
- **Smooth Checkout**: Streamlined multi-step checkout process with address management.

### 👤 User Management
- **JWT Authentication**: Secure login and registration system.
- **Profile Management**: Update personal info and track account activity.
- **Address Book**: Manage multiple shipping addresses with default settings.
- **Order History**: Track order status and view detailed transaction history.

### ⚙️ Admin Dashboard
- **Product Management**: Full CRUD operations with image upload support.
- **Order Processing**: Manage customer orders and update shipping statuses.
- **User Administration**: Monitor user accounts and roles.
- **Promotions Management**: Create and manage marketing codes and discounts.
- **Analytics**: Overview of store performance and statistics.

---

## 🛠️ Technology Stack

| Layer | Technologies |
|-------|--------------|
| **Backend** | Quarkus 3.15, Java 21, RESTeasy Reactive, Jackson |
| **Persistence** | Hibernate ORM with Panache, MySQL 8.0 |
| **Frontend** | Qute Templates, Vanilla JavaScript (ES6+), Bootstrap Icons |
| **Tools** | Maven, Lombok, JWT (JSON Web Tokens) |

---

## 🏗️ Getting Started

### Prerequisites
- **JDK 21** or higher
- **MySQL 8.0**
- **Maven 3.9+**

### Database Setup
1. Create a MySQL database named `phone_store`.
2. Import the schema and sample data:
   ```bash
   mysql -u your_user -p phone_store < Database.sql
   ```
3. Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   quarkus.datasource.username=your_user
   quarkus.datasource.password=your_password
   quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/phone_store
   ```

### Running the Application
Run the application in development mode with live coding:
```shell script
./mvnw quarkus:dev
```
The application will be available at `http://localhost:8080`.

---

## 📂 Project Structure

```text
src/main/java/org/acme/
├── entity/          # JPA Entities (Product, User, Order, etc.)
├── repository/      # Panache Repositories
├── resource/        # REST API Endpoints
├── service/         # Business Logic Layer
├── dto/             # Data Transfer Objects
├── util/            # Helper classes and extensions
└── PageController.java # Qute Template Routing
```

---

## 📖 API Documentation
Detailed information about all REST endpoints and template routes can be found in:
[📄 BACKEND_ENDPOINTS.md](BACKEND_ENDPOINTS.md)

---

## 📜 License
This project is for educational purposes. All rights reserved.

---
*Built with ❤️ using Quarkus and Java 21.*
