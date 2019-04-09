package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

@SpringBootApplication
@EnableZuulProxy
public class WebApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebApplication.class);

    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

}
