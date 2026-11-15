/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll.controller;

import com.payroll.model.Employee;
import com.payroll.service.PayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "Employee Payroll Management System", "section", "802A");
    }

    @GetMapping("/employees")
    public List<Map<String, Object>> getAllEmployees() {
        return payrollService.getAllPayrolls();
    }

    @GetMapping("/departments")
    public List<Map<String, Object>> getDepartmentReport() {
        return payrollService.getDepartmentReport();
    }

    @PostMapping("/employees")
    public ResponseEntity<?> registerEmployee(@RequestBody Map<String, Object> payload) {
        try {
            String name = String.valueOf(payload.get("emp_name"));
            String email = String.valueOf(payload.get("email"));
            String department = String.valueOf(payload.get("department"));
            String designation = String.valueOf(payload.get("designation"));
            double basic = Double.parseDouble(String.valueOf(payload.get("basic_salary")));
            double hra = Double.parseDouble(String.valueOf(payload.get("hra")));
            double da = Double.parseDouble(String.valueOf(payload.get("da")));
            double pf = Double.parseDouble(String.valueOf(payload.get("pf_deduction")));
            double tax = Double.parseDouble(String.valueOf(payload.get("tax_deduction")));

            Employee emp = new Employee(name, email, department, designation, LocalDate.now());
            int newId = payrollService.registerEmployeeWithSalary(emp, basic, hra, da, pf, tax);
            return ResponseEntity.ok(Map.of("message", "Employee registered successfully", "emp_id", newId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployeeAndSalary(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        try {
            if (payload.containsKey("department") && payload.containsKey("designation")) {
                payrollService.updateEmployee(
                        id,
                        String.valueOf(payload.get("department")),
                        String.valueOf(payload.get("designation"))
                );
            }
            if (payload.containsKey("basic_salary")) {
                double basic = Double.parseDouble(String.valueOf(payload.get("basic_salary")));
                double hra = Double.parseDouble(String.valueOf(payload.get("hra")));
                double da = Double.parseDouble(String.valueOf(payload.get("da")));
                double pf = Double.parseDouble(String.valueOf(payload.get("pf_deduction")));
                double tax = Double.parseDouble(String.valueOf(payload.get("tax_deduction")));
                payrollService.updateSalary(id, basic, hra, da, pf, tax);
            }
            return ResponseEntity.ok(Map.of("message", "Payroll record updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        boolean deleted = payrollService.removeEmployee(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}
