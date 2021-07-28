package com.tmt.challenge.dto.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequestDTO {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
