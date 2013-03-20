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
