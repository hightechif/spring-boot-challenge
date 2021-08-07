package com.tmt.challenge.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    @Column(unique = true)
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employee")
    private List<Assignment> assignments = new ArrayList<>();

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

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", assignments=" + assignments +
                '}';
    }
}
