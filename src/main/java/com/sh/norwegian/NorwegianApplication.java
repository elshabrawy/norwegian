package com.sh.norwegian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NorwegianApplication {
    public static void main(String[] args) {
        SpringApplication.run(NorwegianApplication.class, args);

    }

}
