package com.tmt.challenge.dto;

public class StudentIdCardDTO {
    // Attributes
    private Long id;
    private String cardNumber;

    // Empty Constructor
    public StudentIdCardDTO() {
    }

    // Constructor with parameters
    public StudentIdCardDTO(Long id, String cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    // toString
    @Override
    public String toString() {
        return "StudentIdCardDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
