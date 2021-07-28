package com.tmt.challenge.dto.response;

public class DefaultResponseDTO {
    // Attributes
    private String status = "200";
    private String message;

    // Empty Constructor
    public DefaultResponseDTO() {
    }

    // Constructor with parameters
    public DefaultResponseDTO(String message) {
        this.message = message;
    }

    public DefaultResponseDTO(String message, Integer status) {
        this.status = status.toString();
        this.message = message;
    }

    // Getter and Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status.toString();
    }

    // toString
    @Override
    public String toString() {
        return "ResponseDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
