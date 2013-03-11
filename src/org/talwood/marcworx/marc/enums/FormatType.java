package org.talwood.marcworx.marc.enums;

import org.talwood.marcworx.marc.constants.MarcLeaderConstants;


public enum FormatType {
    UNKNOWN(MarcLeaderConstants.UNKNOWN, MarcLeaderConstants.DESC_UNKNOWN, -1),
    BIBLIOGRAPHIC(MarcLeaderConstants.BIBLIOGRAPHIC, MarcLeaderConstants.DESC_BIBLIOGRAPHIC, 40),
    AUTHORITY(MarcLeaderConstants.AUTHORITY, MarcLeaderConstants.DESC_AUTHORITY, 40),
    CLASSIFICATION(MarcLeaderConstants.CLASSIFICATION, MarcLeaderConstants.DESC_CLASSIFICATION, 14),
    COMMUNITY(MarcLeaderConstants.COMMUNITY, MarcLeaderConstants.DESC_COMMUNITY, 15),
    HOLDINGS(MarcLeaderConstants.HOLDINGS, MarcLeaderConstants.DESC_HOLDINGS, 32),
    ;

    private int fmtType;
    private String fmtCode;
    private int length008;

    private FormatType(int fmtType, String fmtCode, int length008) {
        this.fmtType = fmtType;
        this.fmtCode = fmtCode;
        this.length008 = length008;
    }

    public static FormatType findByFormatType(int fmtType) {
        FormatType result = UNKNOWN;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getFmtType() == fmtType) {
                result = values()[x];
                break;
            }
        }

        return result;

    }
    public static FormatType findByFormatDescription(String description) {
        FormatType result = UNKNOWN;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getFmtCode().equals(description)) {
                result = values()[x];
                break;
            }
        }

        return result;

    }

    public int getFmtType() {
        return fmtType;
    }

    public String getFmtCode() {
        return fmtCode;
    }

    public int getLength008() {
        return length008;
    }


}
