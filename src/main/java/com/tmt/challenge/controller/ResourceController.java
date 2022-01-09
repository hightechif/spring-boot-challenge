package com.tmt.challenge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping(value = "/hello-user", method = RequestMethod.GET)
    public String helloUser() {
        return "Hello User";
    }

    @RequestMapping(value = "/hello-admin", method = RequestMethod.GET)
    public String helloAdmin() {
        return "Hello Admin";
    }
}
