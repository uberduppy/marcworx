package org.talwood.marcworx.marc.enums;

public enum MarcFileReadStatus {
    ERROR,
    VALID,
    ALLDONE,
    KEYBOARDBREAK,
    INCOMPLETEREAD;

    private MarcFileReadStatus() {
        
    }
}
