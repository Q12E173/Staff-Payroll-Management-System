# Staff Payroll Management System

A Java-based desktop application developed to manage employee records, positions, payroll calculations, and salary slips.

This project was developed as a group project for the Object-Oriented Programming course at Multimedia University (MMU).

## Overview

The Staff Payroll Management System is designed to automate and simplify payroll-related tasks for administrators and employees.

The system provides two main user roles:

- **Admin** – manages employee records, positions, salary calculations, and payslips.
- **Employee** – logs in to view their salary slips and manage their password.

The system supports both full-time and part-time employees and includes payroll calculations involving salary, overtime, allowances, bonuses, deductions, EPF, SOCSO, and EIS.

## Key Features

### Admin

- Admin authentication
- Employee management
  - Add employees
  - Edit employee information
  - Delete employees
  - Search employees
- Position management
  - Add positions
  - Edit position information
  - Delete positions
  - Support for full-time and part-time positions
- Salary calculation
  - Full-time employee salary calculation
  - Part-time employee salary calculation
  - Overtime calculation
  - Allowance calculation
  - Bonus calculation
  - Deduction calculation
  - EPF calculation
  - SOCSO calculation
  - EIS calculation
- Reset employee salary information
- Generate salary slips in PDF format
- Change admin password
- Logout

### Employee

- Employee authentication
- First-time password setup
- View generated salary slips
- Change password
- Logout

## Technologies

- **Java**
- **Java Swing** – Graphical User Interface
- **Object-Oriented Programming (OOP)**
- **JCalendar** – Date selection component
- **iText PDF** – PDF salary slip generation

## System Design

The system follows an object-oriented design with different classes representing employees, positions, tax calculations, and application pages.

The project includes:

- Use Case Diagram
- Class Diagram
- State Diagrams
- GUI design
- Admin and Employee modules

## How to Run

### Prerequisites

* Java JDK installed on your computer
* Windows operating system
* Required library files included in the repository

### 1. Open the `src` folder

Open Command Prompt and navigate to the `src` directory.

### 2. Compile the program

Run:

```bash
javac -cp ".;../lib/itextpdf-5.5.9.jar" *.java
```

### 3. Run the program

Run:

```bash
java -cp ".;../lib/itextpdf-5.5.9.jar" MainPage
```

The Staff Payroll Management System will then launch.

## Default Admin Account

The following credentials are provided for demonstration purposes:

```text
Username: admin
Password: admin
```

## Screenshots

### Login Page

<img width="755" height="448" alt="image" src="https://github.com/user-attachments/assets/67639dcf-4a96-4ea9-a4aa-d1f376553250" />

### Admin Menu

<img width="677" height="504" alt="image" src="https://github.com/user-attachments/assets/7923b40c-0fa5-4472-b0a2-113712a0457b" />

### Employee Management

<img width="755" height="488" alt="image" src="https://github.com/user-attachments/assets/bdb1a499-3038-4464-9dda-9c1b176bb8ff" />
<img width="755" height="488" alt="image" src="https://github.com/user-attachments/assets/96f2d1a0-e4ce-4cd9-87ea-2972dc528a38" />
<img width="755" height="541" alt="image" src="https://github.com/user-attachments/assets/35871631-33d3-4a9e-bc1a-b11618e34ea7" />
<img width="755" height="565" alt="image" src="https://github.com/user-attachments/assets/dbbe509a-ef45-4cd6-aafa-4b6f11b2d70a" />


### Salary Calculation

<img width="755" height="421" alt="image" src="https://github.com/user-attachments/assets/f6fe8044-37ac-48ee-b84e-c11394d0d65b" />
<img width="755" height="429" alt="image" src="https://github.com/user-attachments/assets/6a75a991-38b9-4a8a-a74e-33e6583ff06b" />


### Salary Slip

<img width="750" height="756" alt="image" src="https://github.com/user-attachments/assets/1ce7874a-cf76-450d-8e17-2ec9b67e0415" />


