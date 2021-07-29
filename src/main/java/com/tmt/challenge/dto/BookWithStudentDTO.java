package com.tmt.challenge.dto;

import com.tmt.challenge.model.Student;

import java.util.Date;
import java.util.List;

public class BookWithStudentDTO {
    // Attributes
    private Long id;
    private String bookName;
    private Date createdAt;
    private List<StudentOnlyDTO> students;

    // Empty Constructor
    public BookWithStudentDTO() {
    }

    // Constructor with parameters
    public BookWithStudentDTO(Long id, String bookName, Date createdAt, List<StudentOnlyDTO> students) {
        this.id = id;
        this.bookName = bookName;
        this.createdAt = createdAt;
        this.students = students;
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

    public List<StudentOnlyDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentOnlyDTO> students) {
        this.students = students;
    }
}

