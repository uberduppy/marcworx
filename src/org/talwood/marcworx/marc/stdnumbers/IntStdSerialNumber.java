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


public final class IntStdSerialNumber implements BaseStandardNumber {


    private static final String NUMVALUES = "0123456789X-";

    public static final int MAX_ISSN_LENGTH = 8;

    private String raw;
    private String displayable;
    private String sortable;
    private StandardNumberDefinitions errNo = StandardNumberDefinitions.SUCCESS;
    private char checksum;

    public IntStdSerialNumber(String rawISSN) {

        if (rawISSN == null) {
            errNo = StandardNumberDefinitions.CONSTRUCTOR_MISSING_ARGUMENT;
            return;
        }

        this.raw = rawISSN.trim();
        String issn = raw.toUpperCase();
        int tmpsum=0;
        int weight=8;
        StringBuilder sb = new StringBuilder();

        int i;
        for (i = 0; i < issn.length() && weight > 0; i++) {
            int val=NUMVALUES.indexOf(issn.charAt(i));
            if (val>=0) {
                if (val==10 && weight!=1) {
                    errNo = StandardNumberDefinitions.CHECKSUM_DIGIT_IN_WRONG_PLACE;
                    break;
                }
                if (val<11){ //not a dash
                    tmpsum+=weight*val;
                    sb.append(issn.charAt(i));
                    weight--;
                }
            } else {
                errNo = StandardNumberDefinitions.INVALID_NUMBER;
            }
        }
        // must have correct # of digits in final IntStdSerialNumber
        if (errNo.isSuccess()) {
            if (sb.length() < MAX_ISSN_LENGTH || "00000000".equals(sb.toString()) || "00000001".equals(sb.toString()) ) {
                errNo = StandardNumberDefinitions.INVALID_NUMBER;
            }
        }

        if(errNo.isSuccess()) {
            tmpsum%=11;
            if (tmpsum!=0) {
                int val=NUMVALUES.indexOf(issn.charAt(MAX_ISSN_LENGTH-1))-tmpsum;
                if(val<0) {
                    val+=11;
                }
                checksum = NUMVALUES.charAt(val);
                errNo=StandardNumberDefinitions.INVALID_CHECKSUM;
            } else {
                this.sortable = sb.toString();
                convertSortableToDisplayable(this.sortable,raw.substring(i));
            }
        }

    }

    private void convertSortableToDisplayable( String sortable, String extra ) {
        StringBuilder result = new StringBuilder(sortable);
        result.insert(4,'-');

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
        if (MarcWorxStringHelper.isNotEmpty(newExtra)) {
            result.append(" (");
            result.append(newExtra);
            result.append(")");
        }
        displayable = result.toString();
    }

    @Override
    public boolean isValid() {
        return (errNo.isSuccess());
    }

    @Override
    public StandardNumberDefinitions getErrNo() {
        return errNo;
    }

    @Override
    public String getRaw() {
        return raw;
    }
    public char getChecksum() {
        return checksum;
    }

    @Override
    public String getDisplayable() {
        return displayable;
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
    public String getTagName() {
        return "ISSN";
    }

    @Override
    public int getTagNumber() {
        return MarcTagConstants.TAG_ISSN;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntStdSerialNumber)) {
            return false;
        }

        IntStdSerialNumber rhs = (IntStdSerialNumber)o;

        return ((sortable == null && rhs.sortable == null && MarcWorxObjectHelper.equals(raw, rhs.raw))
            || (sortable != null && sortable.equals(rhs.sortable)));
    }
    @Override
    public int hashCode(){
        return MarcWorxObjectHelper.getHashCode(sortable) + MarcWorxObjectHelper.getHashCode(raw);
    }

}
