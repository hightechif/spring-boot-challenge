package com.tmt.challenge.dto;

public class ResponseDTO {
    // Attributes
    private String status = "200";
    private String message;

    // Empty Constructor
    public ResponseDTO() {
    }

    // Constructor with parameters
    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(String message, Integer status) {
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
