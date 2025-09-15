package com.infonet.ocr.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@Order(1)
public class AuthFilter implements Filter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Request URL: " + req.getRequestURI());
        // Add custom logic here, e.g., logging, authentication, etc.
        String clientId = req.getHeader("CLIENT_ID");
        String clientSecret = req.getHeader("CLIENT_SECRET");
        if (req.getRequestURI().contains("/welcome")) {
            chain.doFilter(request, response); // Continue the filter chain
            return;
        } else if(isValidUser(clientId,clientSecret)) {
            chain.doFilter(request, response); // Continue the filter chain
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"message\": \"Invalid user\"}");
        }

    }
    private boolean isValidUser(String clientId, String clientSecret) {
       if (clientId!=null && clientId.equals("InfonetOcr") && clientSecret!=null && clientSecret.equals("rcOtenofnI")) {
           return true;
       } else {
           return false;
       }
    }
}