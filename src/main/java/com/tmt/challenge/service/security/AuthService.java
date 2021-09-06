package com.tmt.challenge.service.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmt.challenge.dto.request.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${services.survey.login}")
    private String LOGIN_URL;

    @Value("${login.username}")
    private String USERNAME;

    @Value("${login.pwd}")
    private String PWD;

    public String login() throws JsonProcessingException {
        logger.info("[survey service][login] LOGIN_URL: {}", LOGIN_URL);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{\"username\": \"" + USERNAME + "\", \"password\": \"" + PWD + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, entity, String.class);
        logger.info("[survey service][login] Status: {}", response.getStatusCodeValue());
        logger.info("[survey service][login] response: {}", response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginResponse res = objectMapper.readValue(response.getBody(), LoginResponse.class);
            String accessToken = res.getData().getAccess_token();
            logger.info("[survey service][login] accessToken: {}", accessToken);
            return accessToken;
        }
        return null;
    }
}
