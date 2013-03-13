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


public final class IntStdBookNumber implements BaseStandardNumber {

    /** Length of IntStdBookNumber not including dashes */
    public static final int MAX_ISBN_LENGTH_10 = 10;
    public static final int MAX_ISBN_LENGTH_13 = 13;

    private static final String NUMVALUES = "0123456789X-";

    private char checksum;
    private String displayable;
    private StandardNumberDefinitions errNo = StandardNumberDefinitions.SUCCESS;
    private String raw;
    private String sortable;
    private int mode;

    public static final int SUCCESS = 0;

    public static final int MODE_UNDEFINED = 0;
    public static final int MODE_10DIGIT = 1;
    public static final int MODE_13DIGIT = 2;

/*----------------------------------------------------------------------------*/


    public IntStdBookNumber(String rawISBN) {
        this(rawISBN, MODE_UNDEFINED);
    }
/*----------------------------------------------------------------------------*/


    public IntStdBookNumber(String rawISBN, int mode) {
        if (rawISBN == null) {
            errNo = StandardNumberDefinitions.CONSTRUCTOR_MISSING_ARGUMENT;
            return;
        }

        this.raw = rawISBN.trim();
        if (mode == MODE_UNDEFINED || mode == MODE_10DIGIT || mode == MODE_13DIGIT) {
            this.mode = mode;
            if (mode == MODE_UNDEFINED || mode == MODE_13DIGIT) {
                errNo = buildISBN_13(this.raw);
                if (errNo.isSuccess()) {
                    this.mode = MODE_13DIGIT;
                }
            }
            if ((mode == MODE_UNDEFINED && (!errNo.isSuccess())) || mode == MODE_10DIGIT) {
                errNo = buildISBN_10(this.raw);
                if (errNo.isSuccess()) {
                    this.mode = MODE_10DIGIT;
                }
            }
        } else {
            this.mode = MODE_UNDEFINED;
            errNo = StandardNumberDefinitions.INVALID_ISBN_MODE;
        }
    }


    private StandardNumberDefinitions buildISBN_13(String isbn) {

        int tmpsum = 0;
        int multiplier = 1;
        StringBuilder sb = new StringBuilder();
        int sortableLength = 0;
        char check = 0;

        int i;
        for (i = 0; i < isbn.length() && sortableLength < MAX_ISBN_LENGTH_13; i++) {
            char thisDigit = isbn.charAt(i);
            int val = NUMVALUES.indexOf(thisDigit);
            if (val >= 0 && val < 10) {
                if  (sortableLength == (MAX_ISBN_LENGTH_13 - 1)) {
                    check = NUMVALUES.charAt((10 - (tmpsum % 10)) % 10);
                    if  (check != thisDigit) {
                        checksum = check;
                        return StandardNumberDefinitions.INVALID_CHECKSUM;
                    }
                } else {
                    tmpsum += multiplier * val;
                    multiplier = (multiplier == 1 ? 3 : 1);
                }
                sb.append(thisDigit);
                sortableLength++;
            } else if (val != 11) {
                return StandardNumberDefinitions.INVALID_NUMBER;
            }
        }

        // The number cannot be followed by a number excluding one or more trailing "-"
        int workIndex = i;
        while (workIndex < isbn.length() && NUMVALUES.indexOf(isbn.charAt(workIndex)) == 11) {
            workIndex++;
        }
        if  (workIndex < isbn.length()) {
            char thisDigit = isbn.charAt(workIndex);
            int idx = NUMVALUES.indexOf(thisDigit);
            if ((idx >= 0) && (idx < 10)) {
                return StandardNumberDefinitions.INVALID_NUMBER;
            }
        }

        String temp = sb.toString();
        if (sb.length() < MAX_ISBN_LENGTH_13 || "00000000000000".equals(temp)) {
            return StandardNumberDefinitions.INVALID_NUMBER;
        }

        sortable = temp;
        checksum = check;
        convertSortableToDisplayable_13(sortable, isbn.substring(i));
        return StandardNumberDefinitions.SUCCESS;
    }

/*----------------------------------------------------------------------------*/
    private StandardNumberDefinitions buildISBN_10(String isbn) {

        int tmpsum = 0;
        int multiplier = 10;
        StringBuilder sb = new StringBuilder();
        int sortableLength = 0;
        char check = 0;

        String workISBN = isbn.toUpperCase();

        int i;
        for (i = 0; i < workISBN.length() && sortableLength < MAX_ISBN_LENGTH_10; i++) {
            char thisDigit = workISBN.charAt(i);
            int val = NUMVALUES.indexOf(thisDigit);
            if ((val >= 0 && val < 10) || (val == 10 && sortableLength == 9)) {
                if  (sortableLength == (MAX_ISBN_LENGTH_10 - 1)) {
                    check = NUMVALUES.charAt((11 - (tmpsum % 11)) % 11);
                    if  (check != thisDigit) {
                        checksum = check;
                        return StandardNumberDefinitions.INVALID_CHECKSUM;
                    }
                } else {
                    tmpsum += multiplier * val;
                    multiplier--;
                }
                sb.append(thisDigit);
                sortableLength++;
            } else if (val == 10&& sortableLength != 9) {
                    return StandardNumberDefinitions.CHECKSUM_DIGIT_IN_WRONG_PLACE;
            } else if (val != 11) {
                return StandardNumberDefinitions.INVALID_NUMBER;
            }
        }

        // The number cannot be followed by a number excluding one or more trailing "-"
        int workIndex = i;
        while (workIndex < isbn.length() && NUMVALUES.indexOf(isbn.charAt(workIndex)) == 11) {
            workIndex++;
        }
        if  (workIndex < isbn.length()) {
            char thisDigit = isbn.charAt(workIndex);
            int idx = NUMVALUES.indexOf(thisDigit);
            if ((idx >= 0) && (idx < 10)) {
                return StandardNumberDefinitions.INVALID_NUMBER;
            }
        }

        String temp = sb.toString();
        if (sb.length() < MAX_ISBN_LENGTH_10 || "0000000000".equals(temp)) {
            return StandardNumberDefinitions.INVALID_NUMBER;
        }

        sortable = temp;
        checksum = check;
        convertSortableToDisplayable_10(sortable, isbn.substring(i));
        return StandardNumberDefinitions.SUCCESS;
    }


    private void convertSortableToDisplayable_10(String sortable, String extra) {

            displayable = createDisplayable_Format_10(sortable, extra);
    }


    private void convertSortableToDisplayable_13(String sortable, String extra) {

            displayable = sortable.substring(0, 3) +
                "-" + createDisplayable_Format_10(sortable.substring(3), extra);
    }

    private String createDisplayable_Format_10(String sortable, String extra) {
        StringBuffer result = null;

        int middleHyphen = 0;

        if (sortable.length() == MAX_ISBN_LENGTH_10) {
            int i;

            switch (sortable.charAt(0)) {
                case '0' :
                case '2' :
                case '3' :
                case '4' :
                case '5' :
                case '6' :
                    i = Integer.parseInt(sortable.substring(1, 3));
                    if (i >= 00 && i <= 19)
                        middleHyphen = 3;
                    if (i >= 20 && i <= 69)
                        middleHyphen = 4;
                    if (i >= 70 && i <= 84)
                        middleHyphen = 5;
                    if (i >= 85 && i <= 89)
                        middleHyphen = 6;
                    if (i >= 90 && i <= 94)
                        middleHyphen = 7;
                    if (i >= 95 && i <= 99)
                        middleHyphen = 8;
                    break;

                case '1' :
                    i = Integer.parseInt(sortable.substring(1, 5));
                    if (i >= 5500 && i <= 8697)
                        middleHyphen = 6;
                    if (i >= 8698 && i <= 9989)
                        middleHyphen = 7;
                    if (i >= 9990 && i <= 9999)
                        middleHyphen = 8;
                    break;

                default :
                    break;
            }
        }

        result = new StringBuffer(sortable);
        result.insert(9, '-');
        if (middleHyphen != 0) {
            result.insert(middleHyphen, '-');
        }
        result.insert(1, '-');

        int firstChar;
        int lastChar;
        int parenOpen = extra.indexOf('(');
        if(parenOpen>=0) {
            firstChar = parenOpen+1;
        } else {
            firstChar=0;
        }
        int parenClose = extra.lastIndexOf(')');
        if(parenClose>=0) {
            lastChar = parenClose;
        } else {
            lastChar = extra.length();
        }
        String newExtra = extra.substring(firstChar,lastChar);
        if (":".equals(MarcWorxStringHelper.rightMost(newExtra, 1))) {
            newExtra = MarcWorxStringHelper.leftMost(newExtra, newExtra.length() - 1);
            newExtra = newExtra.trim();
        }
        if (MarcWorxStringHelper.isNotEmpty(newExtra)) {
            result.append(" (");
            result.append(newExtra);
            result.append(")");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntStdBookNumber)) {
            return false;
        }

        IntStdBookNumber rhs = (IntStdBookNumber) o;

        return (
            (sortable == null && rhs.sortable == null && MarcWorxObjectHelper.equals(raw, rhs.raw))
                || (sortable != null && sortable.equals(rhs.sortable)));
    }

    @Override
    public int hashCode(){
        return MarcWorxObjectHelper.getHashCode(sortable) + MarcWorxObjectHelper.getHashCode(raw);
    }

    public char getChecksum() {
        return checksum;
    }

    @Override
    public String getDisplayable() {
        return displayable;
    }

    @Override
    public StandardNumberDefinitions getErrNo() {
        return errNo;
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
    public String getTagName() {
        return "ISBN";
    }

    @Override
    public int getTagNumber() {
        return MarcTagConstants.TAG_ISBN;
    }

    @Override
    public boolean isValid() {
        return (errNo.isSuccess());
    }

    public int getMode() {
        return mode;
    }

    @Override
    public String getSortableExtended() {
        String result = getSortable();
        if(getMode() == MODE_10DIGIT) {
            result = makeISBN13From10(result);
        }
        return result;
    }


    private String makeISBN13From10(String isbn10) {
        StringBuilder sb = new StringBuilder();
        sb.append("978");
        sb.append(isbn10.substring(0, IntStdBookNumber.MAX_ISBN_LENGTH_10 - 1));
        int tmpsum = 0;
        int multiplier = 1;
        for(int i = 0; i < sb.length(); i++) {
            char thisDigit = sb.charAt(i);
            int val = NUMVALUES.indexOf(thisDigit);
            tmpsum += multiplier * val;
            multiplier = (multiplier == 1 ? 3 : 1);
        }
        sb.append(NUMVALUES.charAt((10 - (tmpsum % 10)) % 10));
        return sb.toString();
    }
}
