package org.he.imageuploader.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@Component
@Slf4j
class DeleteDirectory implements FileVisitor<Object> {

    Boolean deleteFileByFile(Path file) throws IOException {
        return Files.deleteIfExists(file);
    }


    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        val filePath = (Path) file;
        var success = deleteFileByFile(filePath);

        if (success) {
            log.info("🚧 visitFile() Deleted: " + filePath.getFileName());
        }
        else {
            log.info("🚧 visitFile() Not Deleted: " + filePath.getFileName());
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        if (exc == null) {
            val dirPath = (Path) dir;
            log.info("🚧 postVisitDirectory() #: >>> postVisitDirectory().Visited: " + dirPath.getFileName());
            var success = deleteFileByFile(dirPath);

            if (success) {
                log.info("🚧 postVisitDirectory() #: >>>>>> Deleted: " + dirPath.getFileName());
            }
            else {
                log.info("🚧 postVisitDirectory() #: >>>>>> Not Deleted: " + dirPath.getFileName());
            }
        }
        else {
            throw exc;
        }
        return FileVisitResult.CONTINUE;
    }
}
