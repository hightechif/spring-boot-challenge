package com.tmt.challenge.model.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmt.challenge.model.Department;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Department department;
    @Column(name = "employee_id")
    private Long employeeId;

    public EmployeeId() {
    }

    public EmployeeId(Department department, Long employeeId) {
        this.department = department;
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeId that = (EmployeeId) o;
        return Objects.equals(department, that.department) && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employeeId);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

}
