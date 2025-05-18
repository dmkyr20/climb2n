package com.github.dmkyr20.climb2n.web;

import com.github.dmkyr20.climb2n.security.UserAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    private final UserAuthenticator authenticator;

    @Autowired
    ApplicationController(UserAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        try {
            authenticator.authenticate(request);
            return "index";
        } catch (AuthenticationException e) {
            return "unauthorized";
        }
    }

    @GetMapping("/start")
    public String start() {
        return "main";
    }
}
