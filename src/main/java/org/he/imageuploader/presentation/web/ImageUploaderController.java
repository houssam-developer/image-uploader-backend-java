package org.he.imageuploader.presentation.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.he.imageuploader.domain.service.StorageService;
import org.he.imageuploader.model.StoreFileReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // TO GET FILE IN WEB BROWSER
    @GetMapping(value = "/files/{filename:.+}",  consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        log.info("📡 serveFile()");

        val data = storageService.loadAsBytes(filename);
        return ResponseEntity.ok(data);
    }

//    // TO DOWNLOAD FILE
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        val file = storageService.loadAsResource(filename);
//
//        val headerValues ="attachment; filename=\"" + file.getFilename() + "\"";
//
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
//                .body(file);
//    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Message {
        private String msg;
    }
}
