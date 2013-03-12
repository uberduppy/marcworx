/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
