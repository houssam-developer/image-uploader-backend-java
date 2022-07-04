package org.he.imageuploader.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.he.imageuploader.domain.service.StorageService;
import org.he.imageuploader.model.StoreFileReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.he.imageuploader.utils.CommonAssertions.assertIsValidString;

@Slf4j()
@Service
public class DefaultStorageService implements StorageService {
    private Path rootStorage;
    private DeleteDirectory deleteDirectory;
    
    @Autowired
    public DefaultStorageService(DeleteDirectory deleteDirectory) {
        this.deleteDirectory = deleteDirectory;
        try {
            this.rootStorage =
                    Paths.get(
                            Paths.get(this.getClass().getClassLoader().getResource("static").toURI()).toString(),
                            "uploads"
                    );
        } catch(Exception exception) {
            log.info("🚫 () #exception: " + exception);
        }
    }

    @Override
    public Boolean init() {
        log.info("🚧 init()");
        return cleanRootStorage();
    }

    @Override
    public CompletableFuture<StoreFileReport> store(MultipartFile multipartFile) {
        return CompletableFuture
                .supplyAsync(() -> cleanRootStorage())
                .completeOnTimeout(false, 5000, TimeUnit.MILLISECONDS)
                .thenApply(it -> storeFile(it, multipartFile));
    }


    @Override
    public StoreFileReport storeFile(Boolean isDeleteAllSuccess, MultipartFile multipartFile) {
        log.info("🚧 storeFile() #isDeleteAllSuccess: " + isDeleteAllSuccess);

        if (!isDeleteAllSuccess) {
            log.info("🚩 storeFile() previous delete failed, can not store the new file");
            return new StoreFileReport(false, "previous delete failed, can not store the new file");
        }

        try {
            if (multipartFile.isEmpty()) {
                log.info("🚧 storeFile() 🚩 multipartFile is empty");
                return new StoreFileReport(false, "multipartFile is empty");
            }

            val originalFilename = multipartFile.getOriginalFilename();
            if (!assertIsValidString(originalFilename)) {
                log.info("🚧 storeFile() 🚩 originalFilename is not valid");
                return new StoreFileReport(false, "multipartFile.originalFilename is not valid");
            }

            val destinationFile = this.rootStorage
                    .resolve(Paths.get(originalFilename))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootStorage.toAbsolutePath())) {
                log.info("🚧 storeFile() 🚩 Cannot store file outside current directory");
                return new StoreFileReport(false, "Cannot store file outside current directory");
            }

            try {
                val inputStream = multipartFile.getInputStream();
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

                if (Files.exists(destinationFile)) {
                    val file = Paths.get("uploads", destinationFile.getFileName().toString()).toString();
                    return new StoreFileReport(true, "", file);
                } else {
                    return new StoreFileReport(false, "check if new file exists failed");
                }

            } catch(Exception exception) {
                log.info("🚫 storeFile() Files.copy failed #exception: " + exception);
                return new StoreFileReport(false, "Files.copy failed #exception: " + exception);
            }
        } catch(Exception exception) {
            log.info("🚫 storeFile() #exception: " + exception);
            return new StoreFileReport(false, exception.getMessage());
        }
    }

    @Override
    public Path load(String filename) {
        log.info("🚧 load()");
        return rootStorage.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        log.info("🚧 loadAsResource()");
        try {
            val file = load(filename);
            val resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                log.info("🚧 store() 🚩 Could not read file #filename: " + filename);
                return null;
            }

        } catch(Exception exception) {
            log.info("🚫 loadAsResource() #exception: " + exception);
            return null;
        }
    }

    public Boolean cleanRootStorage() {
        // remove rootStorage folder and content
        deleteAll();

        // create rootStorage
        try {
            val path = Files.createDirectory(rootStorage);
            return Files.exists(path);
        } catch(Exception exception) {
            log.info("🚫 () #exception: " + exception);
            return false;
        }
    }


    @Override
    public void deleteAll() {
        log.info("🚧 deleteAll() #rootLocation: " + rootStorage);

        try {
            if (!Files.exists(rootStorage)) {
                log.info("🚧 deleteAll() targetFolder does not exists aborting operation");
            }

//            Files.walk(rootStorage)
//                    .forEach(it -> {
//                        log.info("|__ 🚧 deleteAll() walk #it: " + it.getFileName());
//                        try {
//                            Files.deleteIfExists(it);
//                        } catch(Exception exception) {
//                            log.info("🚫 deleteAll.walk() #exception: " + exception);
//                        }
//                    });

            val opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
            Files.walkFileTree(rootStorage, opts, Integer.MAX_VALUE, deleteDirectory);
        } catch(Exception exception) {
            log.info("🚫 deleteAll() #exception: " + exception);
        }
    }


}
