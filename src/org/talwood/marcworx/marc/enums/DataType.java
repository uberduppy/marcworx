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
