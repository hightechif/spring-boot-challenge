package com.tmt.challenge.controller;

import com.tmt.challenge.dto.LogAccessDashboardDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.service.LogAccessDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/log-access")
public class LogAccessDashboardController {

    private final LogAccessDashboardService logAccessDashboardService;

    public LogAccessDashboardController(LogAccessDashboardService logAccessDashboardService) {
        this.logAccessDashboardService = logAccessDashboardService;
    }

    @PostMapping("/create-log")
    public ResponseEntity<LogAccessDashboardDTO> createLogExample(@RequestBody LogAccessDashboardDTO dto, HttpServletRequest request) {
        logAccessDashboardService.save(dto, request);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/update-log")
    public ResponseEntity<DefaultResponseDTO> updateLogExample(@RequestParam("id") Long id,@RequestBody LogAccessDashboardDTO dto) {
        DefaultResponseDTO response = logAccessDashboardService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/clean-log")
    public ResponseEntity<String> cleanLogAccessDashboard() {
        String result = logAccessDashboardService.cleanLog();
        return ResponseEntity.ok().body(result);
    }

}
