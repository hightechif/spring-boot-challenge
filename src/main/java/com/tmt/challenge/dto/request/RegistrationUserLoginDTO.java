package com.tmt.challenge.dto.request;

public class RegistrationUserLoginDTO extends UserLoginDTO {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
