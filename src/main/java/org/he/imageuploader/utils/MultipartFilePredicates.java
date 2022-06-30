package org.he.imageuploader.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.function.Predicate;

public final class MultipartFilePredicates {
    private MultipartFilePredicates() {}

    public static Predicate<MultipartFile> multipartFileIsEmpty = it -> it.isEmpty();
    public static Predicate<MultipartFile> multipartFileIsValidOriginalFilename = it -> CommonAssertions.assertIsValidString(it.getOriginalFilename());

}
