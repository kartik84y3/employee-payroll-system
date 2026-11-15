/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll;

import com.payroll.config.DatabaseConfig;
import com.payroll.model.Employee;
import com.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class EmployeePayrollApplication implements CommandLineRunner {

    private final PayrollService payrollService;
    private final DataSource dataSource;

    @Value("${app.cli.enabled:true}")
    private boolean cliEnabled;

    public EmployeePayrollApplication(PayrollService payrollService, DataSource dataSource) {
        this.payrollService = payrollService;
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeePayrollApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("=========================================================================");
        System.out.println("  EMPLOYEE PAYROLL MANAGEMENT SYSTEM | Section: 802A");
        System.out.println("  Team: Kartik Rana (25BCS11387), Akshay Boora (25BCS11028),");
        System.out.println("        Kul Mahajan (25BCS12318)");
        System.out.println("  Web Dashboard Ready at: http://localhost:8080");
        System.out.println("=========================================================================");
        DatabaseConfig.verifyJdbcConnection(dataSource);

        if (!cliEnabled || System.console() == null && "false".equalsIgnoreCase(System.getenv("CLI_ENABLED"))) {
            return;
        }

        // Run interactive CLI in a background thread so Spring Web & CLI both work simultaneously!
        Thread cliThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- MAIN MENU (Web UI also live at http://localhost:8080) ---");
                System.out.println("1. Register New Employee & Calculate Salary");
                System.out.println("2. View All Employee Payroll Records");
                System.out.println("3. Update Employee Salary Structure");
                System.out.println("4. Update Employee Department / Designation");
                System.out.println("5. Generate Department-Wise Payroll Report");
                System.out.println("6. Delete Employee Record");
                System.out.println("0. Exit");
                System.out.print("Select option: ");

                if (!scanner.hasNextLine()) break;
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        try {
                            System.out.print("Enter Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Email: ");
                            String email = scanner.nextLine();
                            System.out.print("Enter Department: ");
                            String dept = scanner.nextLine();
                            System.out.print("Enter Designation: ");
                            String desig = scanner.nextLine();
                            System.out.print("Basic Salary: ");
                            double basic = Double.parseDouble(scanner.nextLine());
                            System.out.print("HRA: ");
                            double hra = Double.parseDouble(scanner.nextLine());
                            System.out.print("DA: ");
                            double da = Double.parseDouble(scanner.nextLine());
                            System.out.print("PF Deduction: ");
                            double pf = Double.parseDouble(scanner.nextLine());
                            System.out.print("Tax Deduction: ");
                            double tax = Double.parseDouble(scanner.nextLine());

                            Employee emp = new Employee(name, email, dept, desig, LocalDate.now());
                            int newId = payrollService.registerEmployeeWithSalary(emp, basic, hra, da, pf, tax);
                            System.out.println("Employee registered successfully with ID: " + newId);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        List<Map<String, Object>> rows = payrollService.getAllPayrolls();
                        System.out.printf("%-5s %-18s %-14s %-18s %-12s %-12s %-12s %-12s%n",
                                "ID", "Name", "Department", "Designation", "Basic", "Allowances", "Deductions", "Net Salary");
                        System.out.println("-".repeat(108));
                        for (Map<String, Object> r : rows) {
                            System.out.printf("%-5s %-18s %-14s %-18s %-12s %-12s %-12s %-12s%n",
                                    r.get("emp_id"), r.get("emp_name"), r.get("department"), r.get("designation"),
                                    r.get("basic_salary"), r.get("total_allowances"), r.get("total_deductions"), r.get("net_salary"));
                        }
                    }
                    case 3 -> {
                        try {
                            System.out.print("Enter Employee ID: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            System.out.print("New Basic Salary: ");
                            double basic = Double.parseDouble(scanner.nextLine());
                            System.out.print("New HRA: ");
                            double hra = Double.parseDouble(scanner.nextLine());
                            System.out.print("New DA: ");
                            double da = Double.parseDouble(scanner.nextLine());
                            System.out.print("New PF Deduction: ");
                            double pf = Double.parseDouble(scanner.nextLine());
                            System.out.print("New Tax Deduction: ");
                            double tax = Double.parseDouble(scanner.nextLine());
                            payrollService.updateSalary(id, basic, hra, da, pf, tax);
                            System.out.println("Salary updated and Net Salary recalculated.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.print("Enter Employee ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("New Department: ");
                        String dept = scanner.nextLine();
                        System.out.print("New Designation: ");
                        String desig = scanner.nextLine();
                        System.out.println(payrollService.updateEmployee(id, dept, desig)
                                ? "Employee updated." : "Employee ID not found.");
                    }
                    case 5 -> {
                        List<Map<String, Object>> report = payrollService.getDepartmentReport();
                        System.out.printf("%-15s %-10s %-14s %-16s %-16s %-16s%n",
                                "Department", "Employees", "Total Basic", "Allowances", "Deductions", "Net Payout");
                        System.out.println("-".repeat(90));
                        for (Map<String, Object> r : report) {
                            System.out.printf("%-15s %-10s %-14s %-16s %-16s %-16s%n",
                                    r.get("department"), r.get("employee_count"), r.get("total_basic"),
                                    r.get("total_allowances"), r.get("total_deductions"), r.get("total_net_payout"));
                        }
                    }
                    case 6 -> {
                        System.out.print("Enter Employee ID to delete: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println(payrollService.removeEmployee(id)
                                ? "Employee deleted." : "Employee ID not found.");
                    }
                    case 0 -> {
                        System.out.println("Exiting Payroll System...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        });
        cliThread.setDaemon(true);
        cliThread.start();
    }
}
