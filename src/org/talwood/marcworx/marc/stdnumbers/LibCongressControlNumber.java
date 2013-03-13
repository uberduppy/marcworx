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

import org.talwood.marcworx.helpers.MarcWorxObjectHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.MarcTagConstants;

public final class LibCongressControlNumber implements BaseStandardNumber {

    private String displayable;
    private StandardNumberDefinitions errNo = StandardNumberDefinitions.SUCCESS;

    private String raw;
    private String sortable;
    private char structureType;

    public LibCongressControlNumber(String rawLCCN) {
        if (rawLCCN == null) {
            errNo = StandardNumberDefinitions.CONSTRUCTOR_MISSING_ARGUMENT;
            return;
        }

        commonInitialize(rawLCCN);

        if (errNo == StandardNumberDefinitions.INVALID_SERIAL_NUMBER) {
            // if we get an invalid serial number, check to see if the rawLCCN
            // contains two LCCNs
            StandardNumberDefinitions oldErrNo = errNo;
            int index = rawLCCN.lastIndexOf(' ');
            if (index != -1) {
                commonInitialize(rawLCCN.substring(0, index));
                if (!errNo.isSuccess()) {
                    // still invalid, revert to old error code
                    errNo = oldErrNo;
                }
            }
        }
    }

/*----------------------------------------------------------------------------*/

    private void commonInitialize(String rawLCCN) {
        this.raw = rawLCCN.trim();
//        String lccn = raw.toLowerCase();
        String lccn = raw;
        StringBuilder sb = new StringBuilder();

        lccn = removeWhitespace(lccn);

        // count prefix characters
        int index = 0;
        for (; index < lccn.length() && Character.isLetter(lccn.charAt(index)); index++) {

        }

        // count digits
        int beginYear = index;
        for (; index < lccn.length() && Character.isDigit(lccn.charAt(index)); index++) {

        }

        int lengthYear = (index - beginYear) % 6;

        structureType = (lengthYear == 4) ? 'B' : 'A';

        int prefixMaxSize = (structureType == 'A') ? 3 : 2;
        if (beginYear > prefixMaxSize) {
            errNo = StandardNumberDefinitions.INVALID_PREFIX_SIZE;
            return;
        }

        int endYear = beginYear + lengthYear;

        switch (lengthYear) {
            case 1 :
                sb.append("0");
                sb.append(lccn.substring(beginYear, endYear));
                break;
            case 2 :
            case 4 :
                sb.append(lccn.substring(beginYear, endYear));
                break;
            default :
                errNo = StandardNumberDefinitions.INVALID_YEAR_LENGTH;
                return;
        }

        // Skip any dashes between year and serial number
        for (index = endYear; index < lccn.length()
            && lccn.charAt(index) == '-'; index++) {

        }

        // raw serial number may be shorter than standard length for serial
        // number
        int beginSerialNumber = index;
        for (; index < lccn.length() && Character.isDigit(lccn.charAt(index)); index++) {

        }

        int lengthSerialNumber = index - beginSerialNumber;
        boolean invalidCharInSerialNumber =
            index < lccn.length()
                && lengthSerialNumber < 6
                && '/' != lccn.charAt(index);

        if (invalidCharInSerialNumber
            || lengthSerialNumber == 0
            || lengthSerialNumber > 6) {

            errNo = StandardNumberDefinitions.INVALID_SERIAL_NUMBER;
            return;
        }

        sb.insert(0, structureType == 'B' ? "  " : "   ");

        for (int i = 6 - lengthSerialNumber; i > 0; i--) {
            sb.append('0');
        }
        sb.append(lccn.substring(beginSerialNumber, index));

        sortable = sb.toString().toLowerCase();

        errNo = StandardNumberDefinitions.SUCCESS;

        convertToDisplayable(lccn.substring(0, beginYear), lccn.substring(index));
    }

/*----------------------------------------------------------------------------*/

    private void convertToDisplayable(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder(prefix);

        int prefixMaxSize = (structureType == 'A') ? 3 : 2;
        while (sb.length() < prefixMaxSize) {
            sb.append(' ');
        }
        sb.append(sortable.substring(prefixMaxSize));
        int dashPosition = prefixMaxSize + (structureType == 'A' ? 2 : 4);
        sb.insert(dashPosition, '-');

        // Remove the zeroes from the displayable
        int index = dashPosition + 1;
        while(index < sb.length() && sb.charAt(index) == '0') {
            sb.deleteCharAt(index);
        }

        if (MarcWorxStringHelper.isNotEmpty(suffix)) {
            sb.append(" ").append(suffix);
        }

        displayable = sb.toString();
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LibCongressControlNumber)) {
            return false;
        }

        LibCongressControlNumber rhs = (LibCongressControlNumber) o;

        return (
            (sortable == null && rhs.sortable == null && MarcWorxObjectHelper.equals(raw, rhs.raw))
                || (sortable != null && sortable.equals(rhs.sortable)));
    }

/*----------------------------------------------------------------------------*/
    @Override
    public int hashCode(){
        return MarcWorxObjectHelper.getHashCode(sortable) + MarcWorxObjectHelper.getHashCode(raw);
    }
/*----------------------------------------------------------------------------*/

    @Override
    public String getDisplayable() {
        return displayable;
    }

    @Override
    public String getRaw() {
        return raw;
    }

    @Override
    public String getSortable() {
        return sortable;
    }

    @Override
    public String getSortableExtended() {
        return sortable;
    }

    @Override
    public boolean isValid() {
        return errNo.isSuccess();
    }

    @Override
    public StandardNumberDefinitions getErrNo() {
        return errNo;
    }

    @Override
    public String getTagName() {
        return "LCCN";
    }

    @Override
    public int getTagNumber() {
        return MarcTagConstants.TAG_LCCN;
    }

    /*----------------------------------------------------------------------------*/

    public static String removeWhitespace(String workString) {
        int length = workString.length();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            if (!Character.isWhitespace(workString.charAt(i))) {
                sb.append(workString.charAt(i));
            }
        }

        return sb.toString();
    }


}
