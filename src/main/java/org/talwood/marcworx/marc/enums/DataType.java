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
package org.talwood.marcworx.marc.enums;

import org.talwood.marcworx.marc.constants.DataTypeConstants;

public enum DataType {
    UNKNOWN(DataTypeConstants.DATA_TYPE_UNKNOWN, "Undefined",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC,
            FormatType.AUTHORITY,
            FormatType.CLASSIFICATION,
            FormatType.COMMUNITY,
            FormatType.HOLDINGS
    }),
    MONOGRAPH(DataTypeConstants.DATA_TYPE_MONOGRAPH, "Monographic component part", new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    SERIAL_COMP_PART(DataTypeConstants.DATA_TYPE_SERIAL_COMP_PART, "Serial component part",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    COLLECTION(DataTypeConstants.DATA_TYPE_COLLECTION, "Collection",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    SUBUNIT(DataTypeConstants.DATA_TYPE_SUBUNIT, "Subunit",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    INTEGRATING(DataTypeConstants.DATA_TYPE_INTEGRATING, "Integrating resource",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    MONOGRAPH_ITEM(DataTypeConstants.DATA_TYPE_MONOGRAPH_ITEM, "Monograph/Item",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    SERIAL(DataTypeConstants.DATA_TYPE_SERIAL, "Serial",  new FormatType[]{FormatType.BIBLIOGRAPHIC}),
    INDIVIDUAL(DataTypeConstants.DATA_TYPE_INDIVIDUAL, "Individual",  new FormatType[]{FormatType.COMMUNITY}),
    ORGANIZATION(DataTypeConstants.DATA_TYPE_ORGANIZATION, "Organization",  new FormatType[]{FormatType.COMMUNITY}),
    PROGRAM(DataTypeConstants.DATA_TYPE_PROGRAM, "Program or service",  new FormatType[]{FormatType.COMMUNITY}),
    EVENT(DataTypeConstants.DATA_TYPE_EVENT, "Event",  new FormatType[]{FormatType.COMMUNITY}),
    OTHER(DataTypeConstants.DATA_TYPE_OTHER, "Other",  new FormatType[]{FormatType.COMMUNITY}),

    ;

    private char code;
    private String description;
    private FormatType[] validTypes;

    private DataType(char code, String description, FormatType[] validTypes) {
        this.code = code;
        this.description = description;
        this.validTypes = validTypes;
    }

    public static DataType findByDataTypeCode(char code, FormatType formatToUse, DataType defaultValue) {
        DataType result = defaultValue;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getCode() == code) {
                for (int i = 0; i < values()[x].getValidTypes().length; i++) {
                    if(formatToUse == values()[x].getValidTypes()[i]) {
                        result = values()[x];
                        break;
                    }
                }
            }
        }

        return result;
    }

    public char getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public FormatType[] getValidTypes() {
        return validTypes;
    }



}
