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

public class StandardNumberConstants {
    public static final int SUCCESS = 0;
    public static final int ERROR_CONSTRUCTOR_MISSING_ARGUMENT = 1;
    public static final String ERROR_TXT_MISSING_ARGUMENT = "Missing argument";
    public static final int ERROR_CHECKSUM_DIGIT_IN_WRONG_PLACE = 2;
    public static final String ERROR_TXT_CHECKSUM_DIGIT_IN_WRONG_PLACE = "Checksum digit in wrong place";
    public static final int ERROR_INVALID_CHECKSUM = 3;
    public static final String ERROR_TXT_INVALID_CHECKSUM = "Invalid checksum";
    public static final int ERROR_INVALID_NUMBER = 4;
    public static final String ERROR_TXT_INVALID_NUMBER = "Invalid number";
    public static final int ERROR_INVALID_PREFIX_SIZE = 5;
    public static final String ERROR_TXT_INVALID_PREFIX_SIZE = "Invalid prefix size";
    public static final int ERROR_INVALID_SERIAL_NUMBER = 6;
    public static final String ERROR_TXT_INVALID_SERIAL_NUMBER = "Invalid serial number";
    public static final int ERROR_INVALID_YEAR_LENGTH = 7;
    public static final String ERROR_TXT_INVALID_YEAR_LENGTH = "Invalid year length";
    public static final int ERROR_INVALID_ISBN_MODE = 8;
    public static final String ERROR_TXT_INVALID_ISBN_MODE = "Invalid ISBN mode";

}
