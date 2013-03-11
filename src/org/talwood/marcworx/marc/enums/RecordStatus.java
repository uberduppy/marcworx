package org.talwood.marcworx.marc.enums;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.constants.RecordStatusConstants;

public enum RecordStatus {
    INCREASE(RecordStatusConstants.RECORD_STATUS_INCREASE, "Increase in encoding level",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC,
            FormatType.AUTHORITY,
            FormatType.CLASSIFICATION
    }),
    CORRECTED(RecordStatusConstants.RECORD_STATUS_CORRECTED, "Corrected or revised",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC,
            FormatType.AUTHORITY,
            FormatType.CLASSIFICATION,
            FormatType.COMMUNITY,
            FormatType.HOLDINGS
    }),
    DELETED(RecordStatusConstants.RECORD_STATUS_DELETED, "Deleted",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC,
            FormatType.AUTHORITY,
            FormatType.CLASSIFICATION,
            FormatType.COMMUNITY,
            FormatType.HOLDINGS
    }),
    NEW(RecordStatusConstants.RECORD_STATUS_NEW, "New",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC,
            FormatType.AUTHORITY,
            FormatType.CLASSIFICATION,
            FormatType.COMMUNITY,
            FormatType.HOLDINGS
    }),
    OBSOLETE(RecordStatusConstants.RECORD_STATUS_OBSOLETE, "Obsolete",
        new FormatType[]{
            FormatType.AUTHORITY
    }),
    INCREASE_PREPUB(RecordStatusConstants.RECORD_STATUS_INCREASE_PREPUB, "Increase in encoding level from prepublication",
        new FormatType[]{
            FormatType.BIBLIOGRAPHIC
    }),
    DELETED_HDG_SPLIT(RecordStatusConstants.RECORD_STATUS_DELETED_HDG_SPLIT, "Deleted; heading split into two or more headings",
        new FormatType[]{
            FormatType.AUTHORITY
    }),
    DELETED_HDG_REPLACED(RecordStatusConstants.RECORD_STATUS_DELETED_HDG_REPLACED, "Deleted; heading replaced by another headings",
        new FormatType[]{
            FormatType.AUTHORITY
    }),

    ;

    private char code;
    private String description;
    private FormatType[] validTypes;

    private RecordStatus(char code, String description, FormatType[] validTypes) {
        this.code = code;
        this.description = description;
        this.validTypes = validTypes;
    }

    public static RecordStatus findByRecordStatusCode(char code, RecordStatus defaultValue) {
        RecordStatus result = defaultValue;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getCode() == code) {
                result = values()[x];
                break;
            }
        }

        return result;
    }

    public static String[] generateDropdownStringsByFormatType(FormatType type) {
        List<String> strings = new ArrayList<String>();
        for(int x = 0; x < values().length; x++) {
            RecordStatus rs = values()[x];
            FormatType[] rts = rs.getValidTypes();
            for(int y = 0; y < rts.length; y++) {
                if(rts[y] == type) {
                    strings.add(rs.getCode() + " - " + rs.getDescription());
                    break;
                }
            }
        }
        String[] results = new String[strings.size()];
        for(int x = 0; x < results.length; x++) {
            results[x] = strings.get(x);
        }

        return results;
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