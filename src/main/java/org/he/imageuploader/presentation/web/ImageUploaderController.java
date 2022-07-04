package org.he.imageuploader.presentation.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.he.imageuploader.domain.service.StorageService;
import org.he.imageuploader.model.StoreFileReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller
@CrossOrigin("*")
public class ImageUploaderController {
    private StorageService storageService;

    @Autowired
    public ImageUploaderController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public ResponseEntity<Message> greeting() {
        log.info("📡 greeting()");
        val message = new Message("welcome to image-uploader-api");
        return new ResponseEntity(message, HttpStatus.OK);
    }

//    @GetMapping("/")
//    public String greeting() {
//        return "uploadForm";
//    }

    @PostMapping("/upload")
    @ResponseBody
    public CompletableFuture<StoreFileReport> handleFileUpload(@RequestParam("myfile") MultipartFile multipartFile) {
        log.info("📡 handleFileUpload()");
        try {
            HttpHeaders headers = new HttpHeaders();
            return storageService.store(multipartFile);

        } catch(Exception exception) {
            log.info("🚫 handleFileUpload() #exception: " + exception);
            return CompletableFuture.supplyAsync( () -> new StoreFileReport(false, exception.getMessage()));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Message {
        private String msg;
    }
}
