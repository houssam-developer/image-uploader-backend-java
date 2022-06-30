package org.he.imageuploader.domain.service;

import org.he.imageuploader.model.StoreFileReport;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface StorageService {

    Boolean init();

    CompletableFuture<StoreFileReport> store(MultipartFile multipartFile);

    StoreFileReport storeFile(Boolean isDeleteSucces, MultipartFile multipartFile);

    Path load(String filename);

    Resource loadAsResource(String filename);

    Boolean deleteAll();
}
