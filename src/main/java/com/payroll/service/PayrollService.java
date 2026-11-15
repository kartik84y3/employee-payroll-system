/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll.service;

import com.payroll.model.Employee;
import com.payroll.model.SalaryDetails;
import com.payroll.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayrollService {

    private final PayrollRepository repository;

    public PayrollService(PayrollRepository repository) {
        this.repository = repository;
    }

    public int registerEmployeeWithSalary(Employee emp, double basic, double hra, double da, double pf, double tax) {
        int empId = repository.addEmployee(emp);
        if (empId > 0) {
            SalaryDetails salary = new SalaryDetails(empId, basic, hra, da, pf, tax);
            repository.saveOrUpdateSalary(salary);
        }
        return empId;
    }

    public void updateSalary(int empId, double basic, double hra, double da, double pf, double tax) {
        SalaryDetails salary = new SalaryDetails(empId, basic, hra, da, pf, tax);
        repository.saveOrUpdateSalary(salary);
    }

    public List<Map<String, Object>> getAllPayrolls() {
        return repository.getAllEmployeePayrolls();
    }

    public List<Map<String, Object>> getDepartmentReport() {
        return repository.getDepartmentWiseReport();
    }

    public boolean updateEmployee(int empId, String dept, String designation) {
        return repository.updateEmployeeRole(empId, dept, designation) > 0;
    }

    public boolean removeEmployee(int empId) {
        return repository.deleteEmployee(empId) > 0;
    }
}
