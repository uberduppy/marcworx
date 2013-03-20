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
