package com.tmt.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class LogAccessDashboardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2404760274515126203L;

    private long userId;
    private String userName;
    private String reportName;
    private String reportFilter;
    private String pageName;
    private String pageUrl;
    private String appVersion;
    private String deviceUsed;
    private int requestStatus;
    private String requestLog;
    private String sessionKey;
    private Date sessionKeyStartTime;
    private Date sessionKeyExpiredTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModifiedDate;
    private String lastModifiedBy;

    public LogAccessDashboardDTO() {
    }

    public LogAccessDashboardDTO(long userId, String userName, String reportName, String reportFilter, String pageName, String pageUrl, String appVersion, String deviceUsed, int requestStatus, String requestLog, String sessionKey, Date sessionKeyStartTime, Date sessionKeyExpiredTime, String createdBy, String lastModifiedBy) {
        this.userId = userId;
        this.userName = userName;
        this.reportName = reportName;
        this.reportFilter = reportFilter;
        this.pageName = pageName;
        this.pageUrl = pageUrl;
        this.appVersion = appVersion;
        this.deviceUsed = deviceUsed;
        this.requestStatus = requestStatus;
        this.requestLog = requestLog;
        this.sessionKey = sessionKey;
        this.sessionKeyStartTime = sessionKeyStartTime;
        this.sessionKeyExpiredTime = sessionKeyExpiredTime;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportFilter() {
        return reportFilter;
    }

    public void setReportFilter(String reportFilter) {
        this.reportFilter = reportFilter;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceUsed() {
        return deviceUsed;
    }

    public void setDeviceUsed(String deviceUsed) {
        this.deviceUsed = deviceUsed;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestLog() {
        return requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getSessionKeyStartTime() {
        return sessionKeyStartTime;
    }

    public void setSessionKeyStartTime(Date sessionKeyStartTime) {
        this.sessionKeyStartTime = sessionKeyStartTime;
    }

    public Date getSessionKeyExpiredTime() {
        return sessionKeyExpiredTime;
    }

    public void setSessionKeyExpiredTime(Date sessionKeyExpiredTime) {
        this.sessionKeyExpiredTime = sessionKeyExpiredTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "LogAccessDashboardDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportFilter='" + reportFilter + '\'' +
                ", pageName='" + pageName + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", deviceUsed='" + deviceUsed + '\'' +
                ", requestStatus=" + requestStatus +
                ", requestLog='" + requestLog + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", sessionKeyStartTime=" + sessionKeyStartTime +
                ", sessionKeyExpiredTime=" + sessionKeyExpiredTime +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
