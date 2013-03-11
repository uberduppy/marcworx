package org.talwood.marcworx.exception;

public enum MarcExceptionType {
    UNKNOWN_TYPE("Unknown"),
    INPUT_FILE_NOT_FOUND("MARC Input file not found"),
    INPUT_FILE_NOT_READ("Unable to read from MARC input file"),
    UNABLE_TO_FIND_OFFSET("Unable to seek to given offset in file"),
    NUMBER_FORMAT("Unable to parse number from string"),
    INVALID_LEADER("Leader is not correct length"),
    TAG_TOO_SHORT("Tag must contain at least 4 characters"),
    INVALID_WHEN_CODE("An invalid code was encountered for determining when a record should be modified"),
    INVALID_CHECK_CODE("An invalid check code was encountered for determining when a record should be modified"),
    INVALID_CHECK_TYPE("An invalid check type was encountered for determining when a record should be modified"),
    INVALID_SUB_TASK("An invalid sub task was encountered for determining when a record should be modified"),
    INVALID_DATA_CHECK_TYPE("An invalid data check type was encountered for determining when a record should be modified"),
    INVALID_TASK_CODE("An invalid tasks code was encountered for determining when a record should be modified"),
    OUTPUT_FILE_NOT_CREATED("MARC Output file could not be created"),
    INVALID_TASK_CONDITION("An invalid task condition was found in the rules set"),
    BUILD_TAG_MATCH_PROBLEM("To and from tag numbers must be different"),
    JAXB_EXCEPTION("Unable to parse XML via JAXB"),
    WRITE_ERROR("Unable to write to output file."),
    ;

    private String type;

    private MarcExceptionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
