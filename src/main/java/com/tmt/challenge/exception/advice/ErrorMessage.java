package com.tmt.challenge.exception.advice;

import java.util.Date;

public class ErrorMessage {

    private Integer httpStatus;
    private Date date;
    private String message;
    private String description;

    public ErrorMessage(Integer httpStatus, Date date, String message, String description) {
        this.httpStatus = httpStatus;
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
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
