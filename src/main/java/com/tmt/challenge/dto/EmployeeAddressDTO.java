package com.tmt.challenge.dto;

public class EmployeeAddressDTO {

    private Long id;
    private String name;

    public EmployeeAddressDTO() {
    }

    public EmployeeAddressDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "EmployeeAddressDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
