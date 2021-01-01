package com.huajun123;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketApplication.class);
    }
}
