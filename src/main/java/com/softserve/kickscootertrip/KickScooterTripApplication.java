package com.softserve.kickscootertrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KickScooterTripApplication {

    public static void main(String[] args) {
        SpringApplication.run(KickScooterTripApplication.class, args);
    }

}
