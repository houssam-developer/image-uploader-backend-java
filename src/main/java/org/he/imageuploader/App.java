package org.he.imageuploader;

import org.he.imageuploader.domain.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }



    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
          storageService.init();
        };
    }

    class Person {

    }
}
