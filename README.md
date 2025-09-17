# Spring Boot Banking Application

A **self-paced Spring Boot project** designed to practice backend development by building a full-featured banking application with account management, transactions, security, and email notifications.

## Project Overview

This application simulates a basic banking system, allowing users to register, manage accounts, and perform core banking operations. It is intended as a hands-on project to practice Spring Boot, Java, and industry-standard backend development practices.

### Key Features

1. **User Registration & Account Management**
   - Users can sign up and receive a unique **account number**.
   - Basic user information management.

2. **Core Banking Services**
   - **Credit Accounts** – deposit money into user accounts.  
   - **Debit Accounts** – withdraw money from accounts.  
   - **Transfer Funds** – move money between accounts.  
   - **Balance Inquiry** – check account balances.  
   - **Name Inquiry** – validate account holder names.

3. **Email Notifications**
   - Alerts for account registration and transactions.

4. **Security**
   - **Spring Security with JWT** for authentication.
   - Role-based access control to restrict operations for certain users.

5. **Design Patterns & Best Practices**
   - Use of **Builder Pattern**, **DTOs**, and **Model Mapper** for clean code and object management.
   - Refactoring and code organization to follow **industry-standard practices**.

### Technologies Used

- **Spring Boot** – backend framework  
- **Spring Data JPA** – database access  
- **MySQL** – relational database  
- **Spring Security + JWT** – authentication & authorization  
- **Java Mail** – email notifications  
- **Model Mapper** – DTO mapping  

### Getting Started

1. Clone the repository:  
   ```bash
   git clone https://github.com/tamaraghosn/springboot-banking-system.git
