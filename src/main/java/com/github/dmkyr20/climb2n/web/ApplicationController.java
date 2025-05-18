package com.github.dmkyr20.climb2n.web;

import com.github.dmkyr20.climb2n.security.UserAuthenticator;
import com.github.dmkyr20.climb2n.service.TrainingSession;
import com.github.dmkyr20.climb2n.service.TrainingSessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ApplicationController {

    private final UserAuthenticator authenticator;
    private final TrainingSessionService service;

    @Autowired
    ApplicationController(
            UserAuthenticator authenticator,
            TrainingSessionService service) {
        this.authenticator = authenticator;
        this.service = service;
    }

    @GetMapping("/login")
    String login(HttpServletRequest request) {
        try {
            authenticator.authenticate(request);
            return "start";
        } catch (AuthenticationException e) {
            return "unauthorized";
        }
    }

    @GetMapping("/main")
    String start(@RequestParam String sessionName, Model model) {
        model.addAttribute("data", service.start(sessionName));
        return "main";
    }

    @PostMapping("/stop")
    String finish(@RequestBody TrainingSession session, Model model) {
        try {
            service.stop(session);
            return "success";
        } catch (Exception e) {
            model.addAttribute("data", session);
            return "fallback";
        }
    }
}
