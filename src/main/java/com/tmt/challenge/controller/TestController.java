package com.tmt.challenge.controller;

import com.tmt.challenge.dto.request.RegistrationUserLoginDTO;
import com.tmt.challenge.dto.request.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmt.challenge.dto.response.TokenResponseDTO;

import java.util.Objects;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    private static final String REGISTRATION_URL = "http://localhost:8080/register";
    private static final String AUTHENTICATION_URL = "http://localhost:8080/authenticate";
    private static final String HELLO_URL = "http://localhost:8080/hello-admin";
    private static final String REFRESH_TOKEN = "http://localhost:8080/refresh-token";

    private String token;

    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public String getResponse() throws JsonProcessingException {

        String response = null;
        // create user registration object
        RegistrationUserLoginDTO registrationUser = getRegistrationUser();
        // convert the user registration object to JSON
        String registrationBody = getBody(registrationUser);
        // create headers specifying that it is JSON request
        HttpHeaders registrationHeaders = getHeaders();
        HttpEntity<String> registrationEntity = new HttpEntity<>(registrationBody, registrationHeaders);

        try {
            // Register User
            ResponseEntity<String> registrationResponse = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST,
                    registrationEntity, String.class);
            // if the registration is successful
            if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {

                // create user authentication object
                UserLoginDTO authenticationUserLoginDTO = getAuthenticationUser();
                // convert the user authentication object to JSON
                String authenticationBody = getBody(authenticationUserLoginDTO);
                // create headers specifying that it is JSON request
                HttpHeaders authenticationHeaders = getHeaders();
                HttpEntity<String> authenticationEntity = new HttpEntity<>(authenticationBody,
                        authenticationHeaders);

                // Authenticate User and get JWT
                ResponseEntity<TokenResponseDTO> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                        HttpMethod.POST, authenticationEntity, TokenResponseDTO.class);

                // if the authentication is successful
                if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
                    String token = "Bearer " + authenticationResponse.getBody().getToken();
                    HttpHeaders headers = getHeaders();
                    headers.set("Authorization", token);
                    HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                    // Use Token to get Response
                    ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity,
                            String.class);
                    if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                        response = helloResponse.getBody();
                    }
                }
            }
        } catch (Exception ex) {
            // check if exception is due to ExpiredJwtException
            if (ex.getMessage().contains("io.jsonwebtoken.ExpiredJwtException")) {
                // Refresh Token
                refreshToken();
                // try again with refresh token
                response = getData();
            } else {
                System.out.println(ex);
            }
        }
        return response;
    }

    private String getData() {
        String response = null;

        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
        // Use Token to get Response
        ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity,
                String.class);
        if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
            response = helloResponse.getBody();
        }
        return response;

    }

    private void refreshToken() {
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        headers.set("isRefreshToken", "true");
        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
        // Use Token to get Response
        ResponseEntity<TokenResponseDTO> refreshTokenResponse = restTemplate.exchange(REFRESH_TOKEN, HttpMethod.GET, jwtEntity,
                TokenResponseDTO.class);
        if (refreshTokenResponse.getStatusCode().equals(HttpStatus.OK)) {
            token = "Bearer " + Objects.requireNonNull(refreshTokenResponse.getBody()).getToken();
        }

    }

    private RegistrationUserLoginDTO getRegistrationUser() {
        RegistrationUserLoginDTO user = new RegistrationUserLoginDTO();
        user.setUsername("tester");
        user.setPassword("tester");
        user.setRole("ROLE_ADMIN");
        return user;
    }

    private UserLoginDTO getAuthenticationUser() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("tester");
        userLoginDTO.setPassword("tester");
        return userLoginDTO;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private String getBody(final UserLoginDTO userLoginDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(userLoginDTO);
    }

}
