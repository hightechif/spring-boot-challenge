package com.tmt.challenge.controller;

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
import com.tmt.challenge.model.RegristrationUser;
import com.tmt.challenge.model.ResponseToken;
import com.tmt.challenge.model.User;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    private static final String REGISTRATION_URL = "http://localhost:8080/register";
    private static final String AUTHENTICATION_URL = "http://localhost:8080/authenticate";
    private static final String HELLO_URL = "http://localhost:8080/helloadmin";

    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public String getResponse() throws JsonProcessingException {

        String response = null;
        // create user registration object
        RegristrationUser regristrationUser = getRegistrationUser();
        // convert the user registration object to JSON
        String registrationBody = getBody(regristrationUser);
        // create headers specifying that it is JSON request
        HttpHeaders registrationHeaders = getHeaders();
        HttpEntity<String> registrationEntity = new HttpEntity<String>(registrationBody, registrationHeaders);

        try {
            // Register User
            ResponseEntity<String> registrationResponse = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST,
                    registrationEntity, String.class);
            // if the registration is successful
            if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {

                // create user authentication object
                User authenticationUser = getAuthenticationUser();
                // convert the user authentication object to JSON
                String authenticationBody = getBody(authenticationUser);
                // create headers specifying that it is JSON request
                HttpHeaders authenticationHeaders = getHeaders();
                HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
                        authenticationHeaders);

                // Authenticate User and get JWT
                ResponseEntity<ResponseToken> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                        HttpMethod.POST, authenticationEntity, ResponseToken.class);

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
            System.out.println(ex);
        }
        return response;
    }

    private RegristrationUser getRegistrationUser() {
        RegristrationUser user = new RegristrationUser();
        user.setUsername("tester");
        user.setPassword("tester");
        user.setRole("ROLE_ADMIN");
        return user;
    }

    private User getAuthenticationUser() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("tester");
        return user;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private String getBody(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }

}
