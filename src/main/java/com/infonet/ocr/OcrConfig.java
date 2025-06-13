package com.infonet.ocr;

import com.infonet.ocr.filter.LicenseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrConfig {
//    @Bean
//    public PaddlePaddleOCRV4 paddleOCR() {
//        PaddlePaddleOCRV4 paddleOCR = PaddlePaddleOCRV4.INSTANCE;
//        paddleOCR.init();
//        return paddleOCR;
//    }

    @Bean
    public FilterRegistrationBean<LicenseFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<LicenseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LicenseFilter());
        registrationBean.addUrlPatterns("/ocr/*"); // Specify URL patterns
        registrationBean.setOrder(1); // Optional: set order
        return registrationBean;
    }
}
