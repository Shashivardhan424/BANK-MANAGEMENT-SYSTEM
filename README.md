# 🏦 Bank Management System

A desktop-based **Bank Management System** developed using **Java Swing, AWT, JDBC, and MySQL**.
This project provides basic banking functionalities for customers and administrators through a user-friendly graphical interface.

---

## 📌 Features

### Customer Module

* Customer Registration
* Customer Login
* Account Details
* Deposit Money
* Withdraw Money
* Check Balance
* Transaction History
* Logout

### Admin Module

* Admin Login
* Customer Management
* Transaction Management
* Search Customers
* View All Transactions
* Logout

---

## 🛠 Technologies Used

* Java
* Swing
* AWT
* JDBC
* MySQL
* Eclipse IDE

---

## 🗄 Database Tables

The project uses the following tables:

* `accounts`
* `users`
* `transactions`
* `admin`

**Database Name:** `bank_management`

---

## 📂 Project Structure

```text
BankManagementSystem
│
└── src
    └── com.bank
        ├── DatabaseConnection.java
        ├── Login.java
        ├── Register.java
        ├── CustomerDashboard.java
        ├── AccountDetails.java
        ├── Deposit.java
        ├── Withdraw.java
        ├── Balance.java
        ├── TransactionHistory.java
        ├── AdminLogin.java
        ├── AdminDashboard.java
        ├── CustomerManagement.java
        └── TransactionManagement.java
```

---

## 🔄 Project Flow

```text
Login
│
├── Customer
│   ├── Registration
│   ├── Customer Dashboard
│   ├── Account Details
│   ├── Deposit
│   ├── Withdraw
│   ├── Balance
│   └── Transaction History
│
└── Admin
    ├── Admin Login
    ├── Admin Dashboard
    ├── Customer Management
    └── Transaction Management
```

---

## ▶️ How to Run

1. Install Java JDK.
2. Install MySQL Server.
3. Create database: `bank_management`.
4. Create required tables.
5. Add MySQL JDBC Driver to Eclipse.
6. Run `Login.java`.





## 📜 License

This project is created for educational purposes.
