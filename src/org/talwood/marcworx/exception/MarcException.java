package org.talwood.marcworx.exception;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;

public class MarcException extends Exception {
    private static final long serialVersionUID = 1L;
    private String rootException = null;
    private MarcExceptionType exceptionType = MarcExceptionType.UNKNOWN_TYPE;

    public MarcException(String message ) {
        super( buildMessage(MarcExceptionType.UNKNOWN_TYPE, message, null));
    }

    public MarcException(MarcExceptionType exceptionType) {
        super( buildMessage(exceptionType, null, null));
    }

    public MarcException(MarcExceptionType exceptionType, String message) {
        super( buildMessage(exceptionType, message, null));
    }

/*----------------------------------------------------------------------------*/
    public MarcException(String message, Throwable throwable ) {
        super( buildMessage(MarcExceptionType.UNKNOWN_TYPE, message, throwable), throwable);
        setRootException(throwable);
    }

    public MarcException(MarcExceptionType exceptionType, String message, Throwable throwable ) {
        super( buildMessage(exceptionType, message, throwable), throwable);
        setRootException(throwable);
    }

    public MarcException(MarcExceptionType exceptionType, Throwable throwable ) {
        super( buildMessage(exceptionType, null, throwable), throwable);
        setRootException(throwable);
    }

    private static String buildMessage(MarcExceptionType exceptionType, String message, Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        if(exceptionType != MarcExceptionType.UNKNOWN_TYPE) {
            sb.append(exceptionType.getType());
        }
        if(MarcWorxStringHelper.isNotEmpty(message)) {
            sb.append(message);
        }
        if (throwable != null && throwable instanceof MarcException) {
            sb.append("\nWrapping (").append(throwable.getClass().getName()).append(") MESSAGE: ").append(throwable.getMessage());
        }
        return sb.toString();
    }

    private void setRootException(Throwable ex) {
        rootException = getRootExceptionString(ex);
    }

    protected String getRootExceptionString(Throwable ex) {
        StringBuilder exString = new StringBuilder();
        if (ex != null) {
            exString.append(MarcWorxStringHelper.stackTraceToString(ex));
        }
        return exString.toString();
    }

}
