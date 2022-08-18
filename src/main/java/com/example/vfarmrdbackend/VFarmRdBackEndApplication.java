package com.example.vfarmrdbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VFarmRdBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(VFarmRdBackEndApplication.class, args);
    }
}
