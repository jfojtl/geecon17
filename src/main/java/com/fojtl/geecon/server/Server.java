package com.fojtl.geecon.server;

import org.infinispan.manager.DefaultCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Server {

    @Bean
    public DefaultCacheManager cacheManager() throws IOException {
        return new DefaultCacheManager("ispn-config.xml");
    }

    public static void main(String... args) throws IOException {
        SpringApplication.run(Server.class, args);
    }
}
