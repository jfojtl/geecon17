package com.fojtl.geecon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

@Controller
@EnableAutoConfiguration
public class Server {
    public static void main(String... args) {
        SpringApplication.run(Server.class, args);
    }
}
