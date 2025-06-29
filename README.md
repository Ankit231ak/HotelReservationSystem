# 🏨 Hotel Reservation System

A simple **Java console application** that uses **MySQL** to manage hotel room reservations. This project demonstrates basic CRUD (Create, Read, Update, Delete) operations with database connectivity using **JDBC**.

---

## 🚀 Features

- 📌 Reserve a new room
- 📄 View all reservations
- 🔍 Get room number by guest info
- ✏️ Update reservation details
- ❌ Delete a reservation
- 🗃️ MySQL integration using JDBC

---

## 🛠️ Technologies Used

- Java 17+
- JDBC (Java Database Connectivity)
- MySQL 8+
- VS Code or any Java IDE

---

## 🧩 Database Schema

Run this SQL script before using the program:

```sql

CREATE DATABASE IF NOT EXISTS project1;

USE project1;

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100),
    room_number INT,
    contact_number VARCHAR(20),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
