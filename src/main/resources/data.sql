INSERT IGNORE INTO employees (emp_id, emp_name, email, department, designation, join_date) VALUES
(1, 'Aarav Sharma', 'aarav.sharma@company.com', 'Engineering', 'Software Engineer', '2025-06-15'),
(2, 'Priya Verma', 'priya.verma@company.com', 'Engineering', 'Senior Developer', '2024-03-10'),
(3, 'Rohan Mehta', 'rohan.mehta@company.com', 'HR', 'HR Manager', '2023-11-01');

INSERT IGNORE INTO salary_details (salary_id, emp_id, basic_salary, hra, da, pf_deduction, tax_deduction, net_salary) VALUES
(1, 1, 50000.00, 15000.00, 5000.00, 6000.00, 2500.00, 61500.00),
(2, 2, 80000.00, 24000.00, 8000.00, 9600.00, 6500.00, 95900.00),
(3, 3, 60000.00, 18000.00, 6000.00, 7200.00, 3500.00, 73300.00);
