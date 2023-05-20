package com.tmt.challenge.dto;

import com.tmt.challenge.constant.enums.EmploymentStatus;

import java.util.List;

public class EmployeeDTO {

    private Long departmentId;
    private String departmentName;
    private Long employeeId;
    private String email;
    private String name;
    private String phoneNumber;
    private EmploymentStatus employmentStatus;
    private List<EmployeeAddressDTO> address;
    private List<AssignmentDTO> assignments;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long departmentId, String departmentName, Long employeeId, String email, String name, String phoneNumber, EmploymentStatus employmentStatus, List<EmployeeAddressDTO> address, List<AssignmentDTO> assignments) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.employeeId = employeeId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.employmentStatus = employmentStatus;
        this.address = address;
        this.assignments = assignments;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public List<EmployeeAddressDTO> getAddress() {
        return address;
    }

    public void setAddress(List<EmployeeAddressDTO> address) {
        this.address = address;
    }

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", employeeId=" + employeeId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", assignments=" + assignments +
                '}';
    }
}
