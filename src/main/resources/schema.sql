CREATE TABLE IF NOT EXISTS employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    designation VARCHAR(50) NOT NULL,
    join_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS salary_details (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT UNIQUE NOT NULL,
    basic_salary DECIMAL(10, 2) NOT NULL,
    hra DECIMAL(10, 2) NOT NULL,
    da DECIMAL(10, 2) NOT NULL,
    pf_deduction DECIMAL(10, 2) NOT NULL,
    tax_deduction DECIMAL(10, 2) NOT NULL,
    net_salary DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE CASCADE
);
