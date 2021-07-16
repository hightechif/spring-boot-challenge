package com.tmt.challenge.dto;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;

import java.time.LocalDate;
import java.util.List;

public class StudentRequestDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private List<BookDTO> books;
    private StudentIdCardDTO studentIdCard;
    private List<CourseDTO> courses;

    public StudentRequestDTO() {
    }

    public StudentRequestDTO(Long id, String firstName, String lastName, String email, LocalDate dateOfBirth, List<BookDTO> books, StudentIdCardDTO studentIdCard, List<CourseDTO> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.books = books;
        this.studentIdCard = studentIdCard;
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

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public StudentIdCardDTO getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(StudentIdCardDTO studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
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
                ", studentIdCard='" + studentIdCard + '\'' +
                ", courses=" + courses +
                '}';
    }
}
