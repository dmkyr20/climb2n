package com.github.dmkyr20.climb2n.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver exceptionResolver;
    private final UserService userService;
    private final JwtTokenizer tokenizer;

    JwtFilter(
            HandlerExceptionResolver exceptionResolver,
            UserService userService,
            JwtTokenizer tokenizer) {
        this.exceptionResolver = exceptionResolver;
        this.userService = userService;
        this.tokenizer = tokenizer;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = extractBearerToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UUID userId = tokenizer.detokenizeUserId(token);
            User user = userService.loadUserById(userId);

            if (isNoAuthenticationPresentAlready()) {
                UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
                        .authenticated(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                setAuthentication(authToken);
            }
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }

    private boolean isNoAuthenticationPresentAlready() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
