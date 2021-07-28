package com.tmt.challenge.dto.request;

import com.tmt.challenge.dto.request.UserLoginDTO;

public class RegistrationUserLoginDTO extends UserLoginDTO {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
