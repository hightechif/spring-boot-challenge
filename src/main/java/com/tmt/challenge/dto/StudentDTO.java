package com.tmt.challenge.dto;

import java.util.List;

public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private List<BookDTO> books;
    private String cardNumber;
    private List<CourseDTO> courses;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String firstName, String lastName, String email, Integer age, List<BookDTO> books, String cardNumber, List<CourseDTO> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.books = books;
        this.cardNumber = cardNumber;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", books=" + books +
                ", cardNumber='" + cardNumber + '\'' +
                ", courses=" + courses +
                '}';
    }
}
