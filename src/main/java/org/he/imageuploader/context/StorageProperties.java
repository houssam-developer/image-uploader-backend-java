package org.he.imageuploader.context;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class StorageProperties {
    private String location = "uploads";
}
