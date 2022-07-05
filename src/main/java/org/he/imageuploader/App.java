package org.he.imageuploader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.he.imageuploader.domain.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@SpringBootApplication
public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

        //new Essai();

    }

//    @Slf4j
//    static class Essai {
//        Path rootStorage;
//
//        public Essai()  {
//            rootStorage = Paths.get(this.getClass().getClassLoader().getResource("").getPath(), "public", "uploads");
//            try {
//                Files.createDirectories(rootStorage);
//            } catch(Exception exception) {
//                log.info("🚫 () #exception: " + exception);
//            }
//            System.out.println(this.getClass().getResource(""));
//        }
//   }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
          storageService.init();
        };
    }

}
