package com.tensquare.mananger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

@SpringBootApplication
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ManagerApplication.class);

    }

    @Bean
    public JwtUtil jwtUtil(){

        return new JwtUtil();

    }
}
