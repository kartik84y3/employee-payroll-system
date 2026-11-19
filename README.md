# PayFlow HR — Employee Payroll Management System (Full-Stack + CLI)

**Course:** OOPs Using Java (Case Study 3)  
**Section:** 802A | **Supervisor:** Er. Shivam Sharma (E16516)  

## Group Members
- **Kartik Rana** — `25BCS11387`
- **Akshay Boora** — `25BCS11028`
- **Kul Mahajan** — `25BCS12381`

## Architecture Overview
- **Backend:** Java + Spring Boot 3.4.0 + `DriverManagerDataSource` + `JdbcTemplate` + REST API (`/api/payroll/*`) + Interactive CLI
- **Database:** MySQL 8.0 (`employee_payroll_db` with `employees` and `salary_details` tables) + H2 MySQL-mode support for free cloud hosting
- **Frontend:** Responsive SaaS Web Dashboard (`src/main/resources/static/index.html` and `frontend/index.html` for Netlify)

## Run Locally in VS Code
1. Start MySQL Server (`root` / `12345678`).
2. Open `EmployeePayrollApplication.java` and press **F5** (or click **Run** above `main()`).
3. Open **http://localhost:8080** in your browser for the Web UI, or use the interactive terminal menu!
