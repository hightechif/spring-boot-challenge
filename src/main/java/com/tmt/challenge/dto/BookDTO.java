package com.tmt.challenge.dto;

import java.util.Date;

public class BookDTO {
    // Attributes
    private Long id;
    private String bookName;
    private Date createdAt;

    // Empty Constructor
    public BookDTO() {
    }

    // Constructor with parameters
    public BookDTO(Long id, String bookName, Date createdAt) {
        this.id = id;
        this.bookName = bookName;
        this.createdAt = createdAt;
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

    // toString
    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
