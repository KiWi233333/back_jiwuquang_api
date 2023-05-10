package com.example.back_jiwuquang_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.example")
public class BackJiWuQuangApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackJiWuQuangApiApplication.class, args);
    }

}
