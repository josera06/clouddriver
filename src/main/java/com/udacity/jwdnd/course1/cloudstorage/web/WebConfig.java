package com.udacity.jwdnd.course1.cloudstorage.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        ViewControllerRegistration r = registry.addViewController("/");
        r.setViewName("/login");
        r.setStatusCode(HttpStatus.OK);

        registry.addViewController("/errors/403").setViewName("/errors/403");
        registry.addViewController("/errors/404").setViewName("/errors/404");
    }
}
