package org.talwood.marcworx.exception;

public class ConstraintException extends Exception {
    
    ConstraintExceptionType type = ConstraintExceptionType.CONSTRIANT;
    
    public ConstraintException(ConstraintExceptionType type, Throwable t) {
        super(null, t);
        this.type = type;
    }

    public ConstraintException(ConstraintExceptionType type, String message, Throwable t) {
        super(message, t);
        this.type = type;
    }

    public ConstraintException(ConstraintExceptionType type, String message) {
        super(message, null);
        this.type = type;
    }

    public ConstraintExceptionType getType() {
        return type;
    }
    
    
}
