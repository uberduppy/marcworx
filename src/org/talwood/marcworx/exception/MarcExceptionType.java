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
