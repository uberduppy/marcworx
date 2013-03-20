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

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.constants.RecordTypeConstants;

public enum RecordType {
    RECORD_TYPE_UNKNOWN(RecordTypeConstants.RECORD_TYPE_UNKNOWN, FormatType.UNKNOWN, "Problem"),
    BIBLIOGRAPHIC_BOOK(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK, FormatType.BIBLIOGRAPHIC, "Language Material"),
    BIBLIOGRAPHIC_MUSIC(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MUSIC, FormatType.BIBLIOGRAPHIC, "Notated Music"),
    BIBLIOGRAPHIC_NOTATED_MUSIC(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_NOTATED_MUSIC, FormatType.BIBLIOGRAPHIC, "Manuscript notated music"),
    BIBLIOGRAPHIC_MAP(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MAP, FormatType.BIBLIOGRAPHIC, "Cartographic material"),
    BIBLIOGRAPHIC_MANU_MAP(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MANU_MAP, FormatType.BIBLIOGRAPHIC, "Manuscript cartographic material"),
    BIBLIOGRAPHIC_PROJ_MEDIUM(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_PROJ_MEDIUM, FormatType.BIBLIOGRAPHIC, "Projected medium"),
    BIBLIOGRAPHIC_NON_MUSIC_RECORDING(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_NON_MUSIC_RECORDING, FormatType.BIBLIOGRAPHIC, "Nonmusical sound recording"),
    BIBLIOGRAPHIC_MUSIC_RECORDING(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MUSIC_RECORDING, FormatType.BIBLIOGRAPHIC, "Musical sound recording"),
    BIBLIOGRAPHIC_2D_GRAPHIC(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_2D_GRAPHIC, FormatType.BIBLIOGRAPHIC, "Two-dimensional nonprojectable graphic"),
    BIBLIOGRAPHIC_COMPUTER_FILE(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_COMPUTER_FILE, FormatType.BIBLIOGRAPHIC, "Computer file"),
    BIBLIOGRAPHIC_KIT(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_KIT, FormatType.BIBLIOGRAPHIC, "Kit"),
    BIBLIOGRAPHIC_MIXED(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MIXED, FormatType.BIBLIOGRAPHIC, "Mixed Material"),
    COMMUNITY_INFO(RecordTypeConstants.RECORD_TYPE_COMMUNITY_INFO, FormatType.COMMUNITY, "Community information"),
    BIBLIOGRAPHIC_3D_ARTIFACT(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_3D_ARTIFACT, FormatType.BIBLIOGRAPHIC, "Three-dimensional artifact or object"),
    BIBLIOGRAPHIC_MANU_MATERIAL(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_MANU_MATERIAL, FormatType.BIBLIOGRAPHIC, "Manuscript language material"),
    HOLDING_UNKNOWN(RecordTypeConstants.RECORD_TYPE_HOLDING_UNKNOWN, FormatType.HOLDINGS, "Unknown holdings"),
    HOLDING_MULTIPART(RecordTypeConstants.RECORD_TYPE_HOLDING_MULTIPART, FormatType.HOLDINGS, "Multipart item holdings"),
    CLASSIFICATION_CLASSIFICATION(RecordTypeConstants.RECORD_TYPE_CLASSIFICATION_CLASSIFICATION, FormatType.CLASSIFICATION, "Classification datas"),
    HOLDING_SINGLE_PART(RecordTypeConstants.RECORD_TYPE_HOLDING_SINGLE_PART, FormatType.HOLDINGS, "Single-part item holdings"),
    HOLDING_SERIAL(RecordTypeConstants.RECORD_TYPE_HOLDING_SERIAL, FormatType.HOLDINGS, "Serial item holdings"),
    AUTHORITY_DATA(RecordTypeConstants.RECORD_TYPE_AUTHORITY_DATA, FormatType.AUTHORITY, "Authority data"),
    ;

    private char code;
    private FormatType formatType;
    private String description;
    private RecordType(char code, FormatType formatType, String description) {
        this.code = code;
        this.formatType = formatType;
        this.description = description;
    }

    public static RecordType findByRecordTypeCode(char code, RecordType defaultValue) {
        RecordType result = defaultValue;
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
            RecordType rt = values()[x];
            if(rt.getFormatType() == type) {
                strings.add(rt.getCode() + " - " + rt.getDescription());
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

    public FormatType getFormatType() {
        return formatType;
    }



}
