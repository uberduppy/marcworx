package org.talwood.marcworx.marc.stdnumbers;

public enum StandardNumberDefinitions {
    SUCCESS(StandardNumberConstants.SUCCESS, ""),
    CONSTRUCTOR_MISSING_ARGUMENT(StandardNumberConstants.ERROR_CONSTRUCTOR_MISSING_ARGUMENT, StandardNumberConstants.ERROR_TXT_MISSING_ARGUMENT),
    CHECKSUM_DIGIT_IN_WRONG_PLACE(StandardNumberConstants.ERROR_CHECKSUM_DIGIT_IN_WRONG_PLACE, StandardNumberConstants.ERROR_TXT_CHECKSUM_DIGIT_IN_WRONG_PLACE),
    INVALID_CHECKSUM(StandardNumberConstants.ERROR_INVALID_CHECKSUM, StandardNumberConstants.ERROR_TXT_INVALID_CHECKSUM),
    INVALID_NUMBER(StandardNumberConstants.ERROR_INVALID_NUMBER, StandardNumberConstants.ERROR_TXT_INVALID_NUMBER),
    INVALID_PREFIX_SIZE(StandardNumberConstants.ERROR_INVALID_PREFIX_SIZE, StandardNumberConstants.ERROR_TXT_INVALID_PREFIX_SIZE),
    INVALID_SERIAL_NUMBER(StandardNumberConstants.ERROR_INVALID_SERIAL_NUMBER, StandardNumberConstants.ERROR_TXT_INVALID_SERIAL_NUMBER),
    INVALID_YEAR_LENGTH(StandardNumberConstants.ERROR_INVALID_YEAR_LENGTH, StandardNumberConstants.ERROR_TXT_INVALID_YEAR_LENGTH),
    INVALID_ISBN_MODE(StandardNumberConstants.ERROR_INVALID_ISBN_MODE, StandardNumberConstants.ERROR_TXT_INVALID_ISBN_MODE),
    ;


    private int code;
    private String message;

    private StandardNumberDefinitions(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StandardNumberDefinitions findByCode(int code) {
        StandardNumberDefinitions result = CONSTRUCTOR_MISSING_ARGUMENT;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getCode() == code) {
                result =  values()[x];
            }
        }
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return (code == StandardNumberConstants.SUCCESS);
    }

}
