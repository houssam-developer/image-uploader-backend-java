package org.he.imageuploader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreFileReport {
    private Boolean isStored = false;
    private String messageError = "";
}
