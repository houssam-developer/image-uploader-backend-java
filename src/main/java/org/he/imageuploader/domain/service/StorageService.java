package org.he.imageuploader.domain.service;

import org.he.imageuploader.model.StoreFileReport;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface StorageService {

    Boolean init();

    CompletableFuture<StoreFileReport> store(MultipartFile multipartFile);

    StoreFileReport storeFile(Boolean isDeleteSuccess, MultipartFile multipartFile);

    Path load(String filename);

    /**
     * to display file in web browser with GET
     * @param filename
     * @return byte[]
     */
    byte[] loadAsBytes(String filename);

    /**
     * to download file as resource with GET
     * @param filename
     * @return Resource
     */
    Resource loadAsResource(String filename);

    void deleteAll();
}
