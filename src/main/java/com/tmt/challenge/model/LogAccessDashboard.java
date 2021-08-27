package com.tmt.challenge.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class LogAccessDashboard extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -5370270035953736127L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String userName;
    private String reportName;
    private String reportFilter;
    private String pageName;
    private Date accessDatetime;
    private String pageUrl;
    @Column(length = 50)
    private String userIp;
    @Column(length = 10)
    private String appVersion;
    @Column(length = 40)
    private String sessionKey;
    private Date sessionKeyStartTime;
    private Date sessionKeyExpiredTime;
    private String deviceUsed;
    private int requestStatus;
    private String requestLog;

    public LogAccessDashboard() {
    }

    public LogAccessDashboard(long id, long userId, String userName, String reportName, String reportFilter, String pageName, Date accessDatetime, String pageUrl, String userIp, String appVersion, String sessionKey, Date sessionKeyStartTime, Date sessionKeyExpiredTime, String deviceUsed, int requestStatus, String requestLog) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.reportName = reportName;
        this.reportFilter = reportFilter;
        this.pageName = pageName;
        this.accessDatetime = accessDatetime;
        this.pageUrl = pageUrl;
        this.userIp = userIp;
        this.appVersion = appVersion;
        this.sessionKey = sessionKey;
        this.sessionKeyStartTime = sessionKeyStartTime;
        this.sessionKeyExpiredTime = sessionKeyExpiredTime;
        this.deviceUsed = deviceUsed;
        this.requestStatus = requestStatus;
        this.requestLog = requestLog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getAccessDatetime() {
        return accessDatetime;
    }

    public void setAccessDatetime(Date accessDatetime) {
        this.accessDatetime = accessDatetime;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
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
}
