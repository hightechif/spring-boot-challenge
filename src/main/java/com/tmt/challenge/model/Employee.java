package com.tmt.challenge.model;

import javax.persistence.*;

@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    private String email;
    private String name;
    private String phoneNumber;

    public Employee() {
    }

    public Employee(EmployeeId employeeId, String email, String name, String phoneNumber) {
        this.employeeId = employeeId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
