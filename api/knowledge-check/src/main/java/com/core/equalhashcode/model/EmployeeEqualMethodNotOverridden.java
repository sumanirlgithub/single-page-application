package com.core.equalhashcode.model;

public class EmployeeEqualMethodNotOverridden {
    private String empId;
    private String name;
    private String designation;
    private double salary;

    public EmployeeEqualMethodNotOverridden() {
    }

    public EmployeeEqualMethodNotOverridden(String empId, String name, String designation, double salary) {
        super();
        this.setEmpId(empId);
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

}