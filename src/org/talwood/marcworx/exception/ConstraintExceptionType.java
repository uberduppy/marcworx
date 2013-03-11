package org.talwood.marcworx.exception;

public enum ConstraintExceptionType {
    
    CONSTRIANT,
    ILLEGAL_ACCESS,
    ILLEGAL_ARGUMENT,
    INVOCATION_TARGET,
    INPUT_FILE_NOT_FOUND,
    OUTPUT_FILE_NOT_CREATED,
    JAXB_EXCEPTION
    ;
    
    private ConstraintExceptionType() {
        
    }
    
}