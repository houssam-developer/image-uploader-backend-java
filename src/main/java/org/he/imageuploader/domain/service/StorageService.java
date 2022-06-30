package org.he.imageuploader.domain.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    Boolean init();

    void store(MultipartFile multipartFile);

    Boolean storeFile(Boolean isDeleteSucces, MultipartFile multipartFile);

    Path load(String filename);

    Resource loadAsResource(String filename);

    Boolean deleteAll();
}
