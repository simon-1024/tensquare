package com.tensquare.eurake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakeServer {
    public static void main(String[] args) {

        SpringApplication.run(EurakeServer.class,args);

    }
}
