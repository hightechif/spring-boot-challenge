package com.tmt.challenge.dto;

public class ResponseDTO {
    // Attributes
    private String message;

    // Empty Constructor
    public ResponseDTO() {
    }

    // Constructor with parameters
    public ResponseDTO(String message) {
        this.message = message;
    }

    // Getter and Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // toString
    @Override
    public String toString() {
        return "ResponseDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
