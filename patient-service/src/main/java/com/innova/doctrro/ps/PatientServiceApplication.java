package com.innova.doctrro.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableDiscoveryClient
//@EnableFeignClients
@EnableReactiveFeignClients
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableReactiveMethodSecurity
@SpringBootApplication
public class PatientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

}
