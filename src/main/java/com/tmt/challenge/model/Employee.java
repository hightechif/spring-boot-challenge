package com.tmt.challenge.model;

import com.tmt.challenge.constant.enums.EmploymentStatus;
import com.tmt.challenge.model.composite.EmployeeId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee implements Serializable {

    @EmbeddedId
    private EmployeeId employeeId;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private Long addressRef;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employee",
            orphanRemoval = true)
    private List<EmployeeAddress> address = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employee")
    private List<Assignment> assignments = new ArrayList<>();

    public Employee() {
    }

    public Employee(EmployeeId employeeId, String email, String name, String phoneNumber, EmploymentStatus employmentStatus) {
        this.employeeId = employeeId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.employmentStatus = employmentStatus;
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

    public Long getAddressRef() {
        return addressRef;
    }

    public void setAddressRef(Long addressRef) {
        this.addressRef = addressRef;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public List<EmployeeAddress> getAddress() {
        return address;
    }

    public void setAddress(List<EmployeeAddress> address) {
        this.address = address;
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
                ", addressRef=" + addressRef +
                ", employmentStatus=" + employmentStatus +
                ", address=" + address +
                ", assignments=" + assignments +
                '}';
    }
}
