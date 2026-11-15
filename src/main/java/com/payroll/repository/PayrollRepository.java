/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll.repository;

import com.payroll.model.Employee;
import com.payroll.model.SalaryDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class PayrollRepository {

    private final JdbcTemplate jdbcTemplate;

    public PayrollRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (emp_name, email, department, designation, join_date) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, emp.getEmpName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDate(5, Date.valueOf(emp.getJoinDate()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
    }

    public void saveOrUpdateSalary(SalaryDetails salary) {
        // Cross-compatible upsert (deletes existing row for emp_id if present, then inserts fresh)
        jdbcTemplate.update("DELETE FROM salary_details WHERE emp_id = ?", salary.getEmpId());
        String sql = "INSERT INTO salary_details (emp_id, basic_salary, hra, da, pf_deduction, tax_deduction, net_salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                salary.getEmpId(),
                salary.getBasicSalary(),
                salary.getHra(),
                salary.getDa(),
                salary.getPfDeduction(),
                salary.getTaxDeduction(),
                salary.getNetSalary());
    }

    public List<Map<String, Object>> getAllEmployeePayrolls() {
        String sql = """
            SELECT e.emp_id, e.emp_name, e.email, e.department, e.designation, e.join_date,
                   COALESCE(s.basic_salary, 0) AS basic_salary,
                   COALESCE(s.hra, 0) AS hra,
                   COALESCE(s.da, 0) AS da,
                   COALESCE(s.hra + s.da, 0) AS total_allowances,
                   COALESCE(s.pf_deduction, 0) AS pf_deduction,
                   COALESCE(s.tax_deduction, 0) AS tax_deduction,
                   COALESCE(s.pf_deduction + s.tax_deduction, 0) AS total_deductions,
                   COALESCE(s.net_salary, 0) AS net_salary
            FROM employees e
            LEFT JOIN salary_details s ON e.emp_id = s.emp_id
            ORDER BY e.emp_id
            """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getDepartmentWiseReport() {
        String sql = """
            SELECT e.department,
                   COUNT(e.emp_id) AS employee_count,
                   COALESCE(SUM(s.basic_salary), 0) AS total_basic,
                   COALESCE(SUM(s.hra + s.da), 0) AS total_allowances,
                   COALESCE(SUM(s.pf_deduction + s.tax_deduction), 0) AS total_deductions,
                   COALESCE(SUM(s.net_salary), 0) AS total_net_payout
            FROM employees e
            LEFT JOIN salary_details s ON e.emp_id = s.emp_id
            GROUP BY e.department
            ORDER BY total_net_payout DESC
            """;
        return jdbcTemplate.queryForList(sql);
    }

    public int updateEmployeeRole(int empId, String department, String designation) {
        String sql = "UPDATE employees SET department = ?, designation = ? WHERE emp_id = ?";
        return jdbcTemplate.update(sql, department, designation, empId);
    }

    public int deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        return jdbcTemplate.update(sql, empId);
    }
}
