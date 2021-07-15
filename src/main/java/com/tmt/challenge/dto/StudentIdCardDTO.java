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

    @Override
    public String toString() {
        return "StudentIdCardDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
