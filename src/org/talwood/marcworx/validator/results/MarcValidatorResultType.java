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
package org.talwood.marcworx.validator.results;

public enum MarcValidatorResultType {

    UNKNOWN(0, "Unknown", "Unknown Value", "", true),
    INVALID_TAGNUM(1, "Invalid", "Invalid tag", "found", false),
    LOCAL_TAGNUM(2, "Local", "Local tag", "found", false),
    INVALID_INDICATOR_1(3, "Invalid", "Invalid first indicator", "found", true),
    INVALID_INDICATOR_2(4, "Invalid", "Invalid second indicator", "found", true),
    OBSOLETE_INDICATOR_1(5, "Obsolete", "Obsolete first indicator", "found", true),
    OBSOLETE_INDICATOR_2(6, "Obsolete", "Obsolete second indicator", "found", true),
    TOO_MANY_NON_RPT_SUBFIELDS(7, "Non-repeatable", "Nonrepeatable subfield", "found multiple times", true),
    INVALID_SUBFIELD(8, "Invalid", "Invalid subfield", "found", true),
    OBSOLETE_SUBFIELD(9, "Obsolete", "Obsolete subfield", "found", true),
    OBSOLETE_TAG(10, "Obsolete", "Obsolete tag", "found", false),
    NONREPEATABLE_TAG(11, "Non-repeatable", "Nonrepeatable tag", "found multiple times in record", false),
    SUBFIELD_DATA_VALIDATION(12, "Invalid Subfield Data", "Invalid data in subfield", "", true),
    TAG_DATA_VALIDATION(13, "Invalid Tag Data", "Invalid data in tag", "", false),
    SUBFIELD_REQUIRED(14, "Required", "Subfield", "is required", true),
    INVALID_006_LENGTH(15, "Required", "Tag", "must be 18 characters in length", false),
    INVALID_008_LENGTH(16, "Required", "Tag", "must be 40 characters in length", false),
    INVALID_FIXED_DATA(17, "Invalid Fixed Data", "Data", "", false),
    INVALID_007_LENGTH(18, "Required", "Tag 007 must be", "characters in length", false),

    ;
    private int typeID;
    private String type;
    private String messageHeader;
    private String messageBody;
    private boolean printFooter;

    public int getTypeID() {
        return typeID;
    }

    private MarcValidatorResultType(int typeID, String type, String messageHeader, String messageBody, boolean printFooter) {
        this.typeID = typeID;
        this.messageHeader = messageHeader;
        this.type = type;
        this.messageBody = messageBody;
        this.printFooter = printFooter;
    }

    public MarcValidatorResultType findByTypeID(int typeID) {
        MarcValidatorResultType result = UNKNOWN;
        for (int x = 0; x < values().length; x++) {
            if (values()[x].getTypeID() == typeID) {
                result = values()[x];
                break;
            }
        }
        return result;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public boolean isPrintFooter() {
        return printFooter;
    }

    public String getType() {
        return type;
    }
    
}
