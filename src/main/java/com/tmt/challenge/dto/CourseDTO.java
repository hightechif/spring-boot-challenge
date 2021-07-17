package com.tmt.challenge.dto;

public class CourseDTO {
    // Attributes
    private Long id;
    private String name;
    private String department;

    // Empty Constructor()
    public CourseDTO() {
    }

    // Constructor with parameters
    public CourseDTO(Long id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // toString
    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
