package com.infonet.ocr.filter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Component
@Order(1)
public class LicenseFilter implements Filter {

    private Date licenseEndDate;
    @PostConstruct
    private void initLicenseDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.set(Calendar.YEAR, 2025);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        licenseEndDate = cal.getTime();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Request URL: " + req.getRequestURI());
        // Add custom logic here, e.g., logging, authentication, etc.
        if(isValidLicenseDate()) {
            chain.doFilter(request, response); // Continue the filter chain
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.setContentType("application/json");
            res.getWriter().write("{\"message\": \"License expired\"}");
        }

    }
    private boolean isValidLicenseDate() {
        Date now = new Date();
        return now.before(licenseEndDate);
    }
}