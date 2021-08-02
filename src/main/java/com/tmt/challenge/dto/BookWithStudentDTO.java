package com.tmt.challenge.dto;

import java.util.Date;

public class BookWithStudentDTO {
    // Attributes
    private Long id;
    private String bookName;
    private Date createdAt;
    private StudentOnlyDTO student;

    // Empty Constructor
    public BookWithStudentDTO() {
    }

    // Constructor with parameters
    public BookWithStudentDTO(Long id, String bookName, Date createdAt, StudentOnlyDTO student) {
        this.id = id;
        this.bookName = bookName;
        this.createdAt = createdAt;
        this.student = student;
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public StudentOnlyDTO getStudent() {
        return student;
    }

    public void setStudent(StudentOnlyDTO student) {
        this.student = student;
    }
}

