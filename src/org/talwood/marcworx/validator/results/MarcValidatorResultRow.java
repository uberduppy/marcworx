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

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.MarcTagConstants;

public class MarcValidatorResultRow {


    public static final int NO_TAG = -1;

    private MarcValidatorResultType type = MarcValidatorResultType.UNKNOWN;
    private String displayData = null;
    private String validatorResults = null;
    private int tagNumber = NO_TAG;

    public MarcValidatorResultRow() {
        this.type = MarcValidatorResultType.UNKNOWN;
        this.displayData = "Unknown";
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, String displayData) {
        this.type = type;
        this.displayData = displayData;
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, String displayData, int tagNumber) {
        this.type = type;
        this.displayData = displayData;
        this.tagNumber = tagNumber;
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, String displayData, int tagNumber, String valResults) {
        this.type = type;
        this.displayData = displayData;
        this.tagNumber = tagNumber;
        this.validatorResults = valResults;
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, char displayData) {
        this.type = type;
        this.displayData = String.valueOf(displayData);
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, char displayData, int tagNumber, String valResults) {
        this.type = type;
        this.displayData = String.valueOf(displayData);
        this.tagNumber = tagNumber;
        this.validatorResults = valResults;
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, char displayData, int tagNumber) {
        this.type = type;
        this.displayData = String.valueOf(displayData);
        this.tagNumber = tagNumber;
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, int tagNumber) {
        this.type = type;
        this.tagNumber = tagNumber;
        this.displayData = getFormattedTagNumber();
    }

    public MarcValidatorResultRow(MarcValidatorResultType type, int tagNumber, String valResults) {
        this.type = type;
        this.tagNumber = tagNumber;
        this.displayData = getFormattedTagNumber();
        this.validatorResults = valResults;
    }

    public MarcValidatorResultType getType() {
        return type;
    }

    public void setType(MarcValidatorResultType type) {
        this.type = type;
    }

    private String getFormattedTagNumber() {
        StringBuilder sb = new StringBuilder();
        if(tagNumber == MarcTagConstants.LEADER) {
            sb.append("LDR");
        } else {
            sb.append(tagNumber);
            while(sb.length() < 3) {
                sb.insert(0, '0');
            }
        }
        return sb.toString();
    }


    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getMessageHeader());
        sb.append(" \"").append(displayData).append("\"");
        if(MarcWorxStringHelper.isNotEmpty(type.getMessageBody())) {
            sb.append(" ").append(type.getMessageBody());
        }
        if(tagNumber != NO_TAG && MarcWorxStringHelper.isNotEmpty(validatorResults)) {
            sb.append(" (").append(validatorResults).append(")");
        }
        if(tagNumber != NO_TAG && type.isPrintFooter()) {
            sb.append(" in ").append(getFormattedTagNumber()).append(" tag");
        }
        sb.append(".");

        return sb.toString();
    }

    public String getDisplayData() {
        return displayData;
    }

    public void setDisplayData(String displayData) {
        this.displayData = displayData;
    }

    public int getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }
    
}
