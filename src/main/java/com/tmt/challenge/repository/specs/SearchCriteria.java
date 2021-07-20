package com.tmt.challenge.repository.specs;

import com.tmt.challenge.constant.enums.SearchOperation;

import java.io.Serializable;

public class SearchCriteria implements Serializable {
    // Attributes
    private static final long serialVersionUID = -3710239820092569369L;

    private String key;
    private Object value;
    private SearchOperation operation;
    private String joinColumnName;
    private Object startDate;
    private Object endDate;

    // Empty Constructor
    public SearchCriteria() {
    }

    // Constructor with parameters
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    // Getter and Setter

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public String getJoinColumnName() {
        return joinColumnName;
    }

    public void setJoinColumnName(String joinColumnName) {
        this.joinColumnName = joinColumnName;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    // toString
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", operation=" + operation +
                ", joinColumnName='" + joinColumnName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
