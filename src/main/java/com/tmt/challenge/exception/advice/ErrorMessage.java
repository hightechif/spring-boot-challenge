package com.tmt.challenge.exception.advice;

import java.util.Date;

public class ErrorMessage {

    private Integer HttpStatus;
    private Date date;
    private String message;
    private String description;

    public ErrorMessage(Integer httpStatus, Date date, String message, String description) {
        HttpStatus = httpStatus;
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public Integer getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        HttpStatus = httpStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
