package com.tmt.challenge.service;

import com.tmt.challenge.dto.LogAccessDashboardDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.LogAccessDashboard;
import com.tmt.challenge.repository.LogAccessDashboardRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.YearMonth;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class LogAccessDashboardService {

    private final Logger logger = LoggerFactory.getLogger(LogAccessDashboardService.class);

    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String UNKNOWN = "unknown";

    private final LogAccessDashboardRepository logAccessDashboardRepository;

    public LogAccessDashboardService(LogAccessDashboardRepository logAccessDashboardRepository) {
        this.logAccessDashboardRepository = logAccessDashboardRepository;
    }

    public void save(LogAccessDashboardDTO dto, HttpServletRequest request) {
        logger.info("Save LogAccessDashboard");
        LogAccessDashboard logAccessDashboard = new LogAccessDashboard();
        logAccessDashboard.setUserId(dto.getUserId());
        logAccessDashboard.setUserName(dto.getUserName());
        logAccessDashboard.setReportName(dto.getReportName());
        logAccessDashboard.setReportFilter(dto.getReportFilter());
        logAccessDashboard.setPageName(dto.getPageName());
        logAccessDashboard.setAccessDatetime(new Date());
        logAccessDashboard.setPageUrl(dto.getPageUrl());
        logAccessDashboard.setUserIp(getClientIp(request));
        logAccessDashboard.setAppVersion(dto.getAppVersion());
        logAccessDashboard.setSessionKey(dto.getSessionKey());
        logAccessDashboard.setSessionKeyStartTime(dto.getSessionKeyStartTime());
        logAccessDashboard.setSessionKeyExpiredTime(dto.getSessionKeyExpiredTime());
        logAccessDashboard.setDeviceUsed(dto.getDeviceUsed());
        logAccessDashboard.setRequestStatus(dto.getRequestStatus());
        logAccessDashboard.setRequestLog(dto.getRequestLog());
        logAccessDashboard.setCreatedBy(dto.getCreatedBy());
        logAccessDashboard.setLastModifiedBy(dto.getLastModifiedBy());

        logAccessDashboardRepository.save(logAccessDashboard);
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!StringUtils.isEmpty(ipAddress) && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }

    public DefaultResponseDTO update(Long id, LogAccessDashboardDTO dto) {
        LogAccessDashboard logAccessDashboard = logAccessDashboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log with id " + id + " not found"));
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        Date createdDate = dto.getCreatedDate();
        String message = "request success. but, nothing changed";
        if (dto.getCreatedBy() != null && !Objects.equals(logAccessDashboard.getCreatedDate(), createdDate)) {
            logAccessDashboard.setCreatedDate(createdDate);
            message = "resource updated successfully";
        }
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

    public String cleanLog(int number) {
        YearMonth currentDate = YearMonth.now();
        int currentMonth = currentDate.getMonthValue();
        int prevMonth = currentDate.minusMonths(1).getMonthValue();
        int currentYear = currentDate.getYear();

        int year = currentYear;
        int month = currentMonth - number;
        if (prevMonth > currentMonth) {
            year = currentYear - 1;
            month = 12 - (number - 1);
        }
        logger.info("current date: " + currentDate);
        logger.info("current month: " + currentMonth + "; previous month: " + prevMonth + "; current year: " + currentYear);
        logger.info("used month: " + month + "; used year: " + year);

        long logsCount = logAccessDashboardRepository.findLogsBeforeTheLastNMonth(year, month);
        logger.debug("Total number of logs before the last " + number + " month = {" + logsCount + "}");
        String message = "Logs before the last " + number + " month already clean.";
        if (logsCount > 0) {
            logAccessDashboardRepository.cleanLogsBeforeTheLastNNMonth(year, month);
            message = "Logs before the last " + number + " month cleaned";
        }
        return message;
    }
}
