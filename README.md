# 💳 Spring Boot Banking System

A full-fledged **backend banking application** built with **Spring Boot**, designed to simulate real-world financial operations.  
This project represents my **first backend project** and marks the start of my journey into **Java Spring Boot development** 🚀

---

## 🌟 Overview

This banking system enables secure user management, authentication, and core financial transactions like account creation, credit, debit, and transfers all integrated with real-time balance updates, transaction tracking, and automated email notifications.

It was developed as a learning project to gain hands-on experience in backend engineering, API design, and system security.

---

## ⚙️ Features

### 👥 User Management
- Account creation with **auto-generated account numbers**
- Role-based access control (**Admin** / **User**)
- Encrypted password storage using **BCrypt**
- Real-time account details (name, balance, number)

### 🔐 Authentication & Security
- **JWT (JSON Web Token)** authentication for secure access
- **Spring Security** integration with custom filters and providers
- Stateless session management  
- Custom entry points for unauthorized access handling

### 💰 Core Banking Operations
- **Credit & Debit** operations with live balance updates  
- **Fund Transfers** between accounts (with validation)
- **Balance & Name Inquiry** endpoints

### 📊 Transaction Management
- Track all transactions by account and date range
- **Bank Statement PDF** generation using *iText*
- **Automated email notifications** for:
  - Account creation
  - Login alerts
  - Fund transfers and statements

### 🧩 Architecture & Design
- Built with **Spring Boot**, **Spring Data JPA**, and **MySQL**
- Clear layered architecture  
  `Controller → Service → Repository`
- Emphasis on **clean code**, **reusability**, and **modularity**
- Uses DTOs, custom responses, and utility classes for structured APIs

---

## 🧠 Technical Highlights

This project implements several real-world backend concepts:
- **JWT-based stateless authentication**
- **Dependency injection** & configuration with Spring Beans
- **Global exception handling**
- **Custom authentication filters** (for token validation)
- **Email service integration** using Spring Boot Mail
- **PDF report generation** with iText

---

## 🧪 Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Database | MySQL |
| ORM | Spring Data JPA |
| Security | Spring Security, JWT |
| PDF Generation | iText |
| Email Service | JavaMailSender |
| Build Tool | Maven |
| IDE | IntelliJ IDEA |

---

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher  
- Maven 3.8+  
- MySQL installed and running

### 1️⃣ Clone the Repository
```
git clone https://github.com/your-username/spring-boot-banking-system.git
cd spring-boot-banking-system
```

### 2️⃣ Configure Database
Create a MySQL database (e.g., banking_system_db) and update your application.properties file:

```
spring.datasource.url=jdbc:mysql://localhost:3306/banking_system_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Run the Application
```
mvn spring-boot:run
```

### 📬 API Endpoints

Below is a list of all available REST API endpoints in the Banking System:

| **Method** | **Endpoint** | **Description** |
|-------------|--------------|-----------------|
| **POST** | `/api/users/create` | Create a new bank account |
| **POST** | `/api/users/login` | Authenticate user and return a JWT token |
| **GET** | `/api/users/user/balanceInquiry/{accountNumber}` | Retrieve account balance |
| **GET** | `/api/users/user/nameInquiry/{accountNumber}` | Retrieve account holder name |
| **POST** | `/api/users/user/credit` | Credit an account |
| **POST** | `/api/users/user/debit` | Debit an account |
| **POST** | `/api/users/user/transfer` | Transfer funds between accounts |
| **GET** | `/api/statements/{accountNumber}?startDate=&endDate=` | Generate a PDF bank statement |

## 📧 Email Notifications

The application automatically sends **real-time email alerts** to keep users informed of their banking activities.  
These notifications ensure transparency, security, and convenience for every account holder.

### ✉️ Notification Types

| Event | Description |
|-------|--------------|
| ✅ **Account Creation Confirmation** | Sent when a new account is successfully registered, including account details. |
| 🔑 **Login Alert** | Notifies the user of successful login activity for security awareness. |
| 💰 **Transaction Alerts** | Triggered on fund transfer actions to confirm completed transactions. |
| 🧾 **Bank Statement Delivery** | Automatically emails a generated **PDF statement** for a given date range upon request. |

Each email is delivered using **Spring Boot’s JavaMailSender**, with customized subjects and message bodies tailored to the event type.
