package com.tmt.challenge.dto;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;

import java.time.LocalDate;
import java.util.List;

public class StudentRequestDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private List<Book> books;
    private String cardNumber;
    private List<Course> courses;

    public StudentRequestDTO() {
    }

    public StudentRequestDTO(Long id, String firstName, String lastName, String email, LocalDate dateOfBirth, List<Book> books, String cardNumber, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Student getStudent() {
        Student student = new Student();
        student.setId(this.getId());
        student.setFirstName(this.getFirstName());
        student.setLastName(this.getLastName());
        student.setEmail(this.getEmail());
        student.setDateOfBirth(this.getDateOfBirth());
        return student;
    }

    @Override
    public String toString() {
        return "StudentRequestDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", books=" + books +
                ", cardNumber='" + cardNumber + '\'' +
                ", courses=" + courses +
                '}';
    }
}
