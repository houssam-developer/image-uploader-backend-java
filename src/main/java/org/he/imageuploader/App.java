package org.he.imageuploader;

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
//        new Essai();

    }

//    static class Essai {
//        Path rootStorage;
//
//        public Essai() {
//            try {
//            this.rootStorage =
//                    Paths.get(
//                            Paths.get(this.getClass().getClassLoader().getResource("static").toURI()).toString(),
//                            "uploads"
//                    );
//
//                Files.deleteIfExists(rootStorage);
//                Files.createDirectory(rootStorage);
//
//            } catch (URISyntaxException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
          storageService.init();
        };
    }

}
