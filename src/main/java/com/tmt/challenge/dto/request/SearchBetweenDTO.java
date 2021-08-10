package com.tmt.challenge.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Optional;

public class SearchBetweenDTO {

    private Optional<String> keyword;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Optional<Date> startDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Optional<Date> endDate;

    public Optional<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(Optional<String> keyword) {
        this.keyword = keyword;
    }

    public Optional<Date> getStartDate() {
        return startDate;
    }

    public void setStartDate(Optional<Date> startDate) {
        this.startDate = startDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

    public void setEndDate(Optional<Date> endDate) {
        this.endDate = endDate;
    }
}
