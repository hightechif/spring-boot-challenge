package com.tmt.challenge.controller;

import com.tmt.challenge.dto.LogAccessDashboardDTO;
import com.tmt.challenge.service.LogAccessDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/log-access")
public class LogAccessDashboardController {

    private final LogAccessDashboardService logAccessDashboardService;

    public LogAccessDashboardController(LogAccessDashboardService logAccessDashboardService) {
        this.logAccessDashboardService = logAccessDashboardService;
    }

    @PostMapping("/create-log")
    public LogAccessDashboardDTO createLogExample(@RequestBody LogAccessDashboardDTO dto, HttpServletRequest request) {
        logAccessDashboardService.save(dto, request);
        return dto;
    }

    @DeleteMapping("/clean-log")
    public ResponseEntity<String> cleanLogAccessDashboard() {
        String result = logAccessDashboardService.cleanLog();
        return ResponseEntity.ok().body(result);
    }

}
