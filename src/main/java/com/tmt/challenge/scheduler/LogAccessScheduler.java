package com.tmt.challenge.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmt.challenge.service.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LogAccessScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${services.survey.clean-log}")
    private String CLEAN_LOG_URL;

    private String accessToken;

    private final AuthService authService;

    @Autowired
    public LogAccessScheduler(AuthService authService) {
        this.authService = authService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanLogDashboard() throws JsonProcessingException {
        logger.info("[survey service][clean log] CLEAN_LOG_URL: {}", CLEAN_LOG_URL);
        this.accessToken = authService.login();
        logger.info("[survey service][clean log] accessToken: {}", this.accessToken);
        if (this.accessToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + this.accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(CLEAN_LOG_URL, HttpMethod.DELETE, entity, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                logger.error("Failed request clean log {}", response.getStatusCodeValue());
            }
            logger.info("[survey service][clean log] Status: {}", response.getStatusCodeValue());
        }
    }
}
