/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll.model;

public class SalaryDetails {
    private int salaryId;
    private int empId;
    private double basicSalary;
    private double hra;
    private double da;
    private double pfDeduction;
    private double taxDeduction;
    private double netSalary;

    public SalaryDetails() {}

    public SalaryDetails(int empId, double basicSalary, double hra, double da, double pfDeduction, double taxDeduction) {
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
        this.pfDeduction = pfDeduction;
        this.taxDeduction = taxDeduction;
        calculateNetSalary();
    }

    public final void calculateNetSalary() {
        double grossSalary = this.basicSalary + this.hra + this.da;
        double totalDeductions = this.pfDeduction + this.taxDeduction;
        this.netSalary = grossSalary - totalDeductions;
    }

    public int getSalaryId() { return salaryId; }
    public void setSalaryId(int salaryId) { this.salaryId = salaryId; }

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }

    public double getHra() { return hra; }
    public void setHra(double hra) { this.hra = hra; }

    public double getDa() { return da; }
    public void setDa(double da) { this.da = da; }

    public double getPfDeduction() { return pfDeduction; }
    public void setPfDeduction(double pfDeduction) { this.pfDeduction = pfDeduction; }

    public double getTaxDeduction() { return taxDeduction; }
    public void setTaxDeduction(double taxDeduction) { this.taxDeduction = taxDeduction; }

    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }
}
