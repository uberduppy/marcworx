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
package org.talwood.marcworx.marc.constants;

public class MarcRecordConstants {
    public static final int UNKNOWN = 0;
    public static final int READ_SUCCESS = 1;


    public static final int MARC_IMPORT_SUCCESS = 0;
    public static final int MARC_IMPORT_FAILURE_LEADER = 1;
    public static final int MARC_IMPORT_FAILURE_NO_TITLE = 2;
    public static final int MARC_IMPORT_FAILURE_BAD_DIRECTORY = 3;
    public static final int MARC_IMPORT_FAILURE_TOO_SMALL = 4;
    public static final int MARC_IMPORT_BAD_TAGNO = 6;
    public static final int MARC_IMPORT_BAD_SUBFIELD_NODATA = 8;
    public static final int MARC_IMPORT_FAILURE_WRONG_INDICATOR_ON_LEADER = 9;
    public static final int MARC_IMPORT_BAD_SUBFIELD_DELIMITER = 10;
    public static final int MARC_IMPORT_TAG_TOO_SMALL = 11;
    public static final int MARC_IMPORT_FAILURE_BAD_245 = 12;

    public static final char CHAR_FIELD_TERMINATOR = '\u001e';
    public static final char CHAR_SUBFIELD_DELIMITER = '\u001f';
    public static final char CHAR_RECORD_TERMINATOR = '\u001d';

    static public final int CONST_USMARC_LEADER_SIZE = 24;
    static public final int CONST_USMARC_DIRECTORY_ENTRY_SIZE = 12;

    static public final char CHARACTER_CODING_SCHEME_MARC_8 = ' ';
    static public final char CHARACTER_CODING_SCHEME_UTF8 = 'a';

    static public final int TAG_MAX_SIZE        = 9998;

    static public final String ALL_SUB_LETTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    
}
