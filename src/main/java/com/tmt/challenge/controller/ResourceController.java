package com.tmt.challenge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping({"/hello-user"})
    public String helloUser() {
        return "Hello User";
    }

    @RequestMapping({"/hello-admin"})
    public String helloAdmin() {
        return "Hello Admin";
    }
}
