package org.he.imageuploader.presentation.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.he.imageuploader.domain.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Message {
        private String msg;
    }
}
