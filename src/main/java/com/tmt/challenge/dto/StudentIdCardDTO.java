package com.tmt.challenge.dto;

public class StudentIdCardDTO {

    private Long id;
    private String cardNumber;

    public StudentIdCardDTO() {
    }

    public StudentIdCardDTO(Long id, String cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }

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

    @Override
    public String toString() {
        return "StudentIdCardDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
