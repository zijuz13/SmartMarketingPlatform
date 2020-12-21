package com.huajun123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.huajun123.mappers")
public class RelationshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(RelationshipApplication.class);
    }
}
