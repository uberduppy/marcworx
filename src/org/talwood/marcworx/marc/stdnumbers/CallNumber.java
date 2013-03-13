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

import java.math.BigDecimal;
import java.util.Comparator;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

public class CallNumber {
    static private final int NORMALIZED_DEWEY_LENGTH = 20;
    static private final int MAX_LENGTH_CALLNUM_PREFIX = 30;
    static private final int NORMALIZED_PREFIX_LENGTH = 20;
    static private final int NORMALIZED_SUFFIX_LENGTH = 20;
    static public final int NORMALIZED_CALLNUMBER_MAX_LENGTH = NORMALIZED_DEWEY_LENGTH
                    + NORMALIZED_PREFIX_LENGTH + NORMALIZED_SUFFIX_LENGTH;

    static private final int SUFFIX_COPY_VOLUME_LENGTH = 8;
    static private final int SUFFIX_CUTTER_LENGTH = 8;

    private static final String CHARS_TO_STRIP = "{}[]()";

    public static final String MAX_DEWEY = "999.99999999999999999";

    public static final int MAIN_DEWEY_LENGTH = 3;

    private String fullPrefix = "";
    private String prefix = "";
    private String dewey = null;
    private String suffix = "";
    private String sortableCallNumber = null;
    private String sortableCallNumberForUpperLimitQuery = null;
    private String callNumber = null;

    public CallNumber(String callNumber) {
        int startDeweyOffset = -1;
        int endDeweyOffset = -1;
        this.callNumber = callNumber;

        if (callNumber != null) {
            int consecutiveDigits = 0;
            for (int i = 0; i < callNumber.length(); i++) {
                char current = callNumber.charAt(i);

                if (Character.isDigit(current)) {
                    if (startDeweyOffset == -1) {
                        if (i == 0) {
                            startDeweyOffset = i;
                        } else if( callNumber.charAt(i -1) == ' ' || callNumber.charAt(i -1) == '/'){
                            startDeweyOffset = i;
                        }
                    }
                    if (startDeweyOffset != -1) {
                        consecutiveDigits++;
                    }
                } else {
                    if(consecutiveDigits == MAIN_DEWEY_LENGTH) {
                        break;
                    } else {
                        startDeweyOffset = -1;
                        consecutiveDigits = 0;
                    }
                }
            }

            int spaceIndex = callNumber.indexOf(' ');

            if(consecutiveDigits != MAIN_DEWEY_LENGTH) {
                if(spaceIndex != -1) {
                    fullPrefix = callNumber.substring(0, spaceIndex);
                    suffix = callNumber.substring(spaceIndex + 1);

                } else {
                    fullPrefix = callNumber;
                }
                prefix = fullPrefix;
            } else {

                if ((spaceIndex == -1 || spaceIndex > startDeweyOffset ) && startDeweyOffset > 0) {
                    spaceIndex = startDeweyOffset;
                } else if (startDeweyOffset == 0){
                    spaceIndex = -1;
                }

                if(spaceIndex != -1) {
                    if (startDeweyOffset > 0 && callNumber.charAt(startDeweyOffset-1) == '/' && spaceIndex >= startDeweyOffset){
                        prefix = callNumber.substring(0, spaceIndex-1);
                    } else {
                        prefix = callNumber.substring(0, spaceIndex);
                    }
                }

                // Determine the end point.
                endDeweyOffset = startDeweyOffset + MAIN_DEWEY_LENGTH ;
                boolean periodFound = false;
                for(int i = startDeweyOffset + MAIN_DEWEY_LENGTH; i < callNumber.length(); i++) {
                    char current = callNumber.charAt(i);
                    if (Character.isDigit(current) || current == '/' || current == '.') {
                        if(current == '.') {
                            if(periodFound) {
                                break;
                            }
                            periodFound = true;
                        } else if (consecutiveDigits == MAIN_DEWEY_LENGTH && current == '/'
                                && (callNumber.length() > i+1
                                    && Character.isDigit(callNumber.charAt(i + 1)))
                                && !periodFound) {
                            // if we've already got a three-digit dewey number, but it is
                            // followed by a slash with another non-decimal digit (i.e. it would make our dewey number 4 or more digits)
                            // we need to break (ex. "404/808.25", dewey = "404")
                            break;
                        } else {
                            endDeweyOffset = i + 1;
                        }
                    } else {
                        break;
                    }
                }
                if (!periodFound) {
                    endDeweyOffset = startDeweyOffset + MAIN_DEWEY_LENGTH;
                }
                // There is a Dewey, parse the trinity.
                if (startDeweyOffset > 0 && callNumber.charAt(startDeweyOffset-1) == '/'){
                    fullPrefix = callNumber.substring(0, startDeweyOffset-1);
                } else {
                    fullPrefix = callNumber.substring(0, startDeweyOffset);
                }
                dewey = MarcWorxStringHelper.removeChars(callNumber.substring(startDeweyOffset, endDeweyOffset), '/');
                dewey = MarcWorxStringHelper.truncate(dewey, 20 ,false);
                if (endDeweyOffset < callNumber.length()) {
                    suffix = callNumber.substring(endDeweyOffset);
                }
                //This is used to make a valid localSuffix from '123.sin' where the localSuffix should just be 'sin'.
                if (dewey.length() == 3) {
                    suffix = MarcWorxStringHelper.trimEnds(suffix, '.');
                }
            }
        }

        suffix = MarcWorxStringHelper.trimEnds(suffix);
        fullPrefix = normalize(fullPrefix);
        prefix = normalize(prefix);
        if (prefix != null) {
            prefix = MarcWorxStringHelper.leftMost(prefix, MAX_LENGTH_CALLNUM_PREFIX);
        }
    }

/*----------------------------------------------------------------------------*/
    public String getSortableCallNumberForUpperLimitQuery(){
        if (sortableCallNumberForUpperLimitQuery == null) {
            sortableCallNumberForUpperLimitQuery = getSortableCallNumber(true);
        }
        return sortableCallNumberForUpperLimitQuery;
    }
    public String getSortableCallNumberWithoutCutter(){
        return MarcWorxStringHelper.leftMost(getSortableCallNumber(false),
            NORMALIZED_PREFIX_LENGTH + NORMALIZED_DEWEY_LENGTH);
    }
    public String getSortableCallNumber() {
        if (sortableCallNumber == null) {
            sortableCallNumber = getSortableCallNumber(false);
        }
        return sortableCallNumber;
    }
    private String getSortableCallNumber(boolean padCallNumberForUseInUpperLimitQuery) {
        String result = "";

        if (!MarcWorxStringHelper.isEmpty(fullPrefix) ||
            !MarcWorxStringHelper.isEmpty(dewey) ||
            !MarcWorxStringHelper.isEmpty(suffix)) {
            // Normalize our call number
            /*
             * If the call number only has a localPrefix, then the localPrefix is it's sort key,
             * otherwise sort it according to our rules.
             */
            if ( !MarcWorxStringHelper.isEmpty(fullPrefix) &&
                MarcWorxStringHelper.isEmpty(dewey) &&
                MarcWorxStringHelper.isEmpty(suffix)) {
                result = normalizePrefix(true);
                if (padCallNumberForUseInUpperLimitQuery) {
                    result = MarcWorxStringHelper.leftJustify(result, NORMALIZED_CALLNUMBER_MAX_LENGTH, 'Z');
                }
            } else {
                result = normalizePrefix(true) +
                         normalizeDewey(padCallNumberForUseInUpperLimitQuery) +
                         normalizeSuffix(padCallNumberForUseInUpperLimitQuery);
            }
        }

        return result;

    }

    public double getDeweyUpperLimit() {
        double result = 0.0;
        double deweyValue = 0.0;
        String workDewey = dewey;
        if(workDewey != null) {
            workDewey = workDewey.trim();
            try {
                // This valueOf simply determines if we are truly looking at a double.
                deweyValue = Double.valueOf(workDewey).doubleValue();
                if(deweyValue >= 0.0 && deweyValue < 1000.0) {
                    int deweyLen = workDewey.length();
                    if(deweyLen > 0) {
                        char lastChar = workDewey.charAt(deweyLen - 1);
                        if(lastChar == '.') {
                            workDewey = workDewey.substring(0, deweyLen - 1);
                            deweyLen--;
                            lastChar = workDewey.charAt(deweyLen - 1);
                        }
                        if(lastChar != '9') {
                            // If last char is not a 9, add one to it.
                            // Else back through, looking for non-9.
                            workDewey = workDewey.substring(0, deweyLen - 1) + (char)(lastChar + 1);
                            result = Double.valueOf(workDewey).doubleValue();
                        } else {
                            int decimalIndex = workDewey.indexOf(".");
                            int numPlacesAfterDecimal = 0;
                            if (decimalIndex != -1){ // we have a decimal point
                                numPlacesAfterDecimal = deweyLen - decimalIndex -1 ;
                            }

                            deweyValue *= (Math.pow(10, numPlacesAfterDecimal));
                            deweyValue += 1;
                            deweyValue /= (Math.pow(10, numPlacesAfterDecimal));

                            workDewey = Double.toString(deweyValue);
                            decimalIndex = workDewey.indexOf(".");
                            workDewey = MarcWorxStringHelper.leftMost(workDewey, decimalIndex+numPlacesAfterDecimal);
                            result = Double.valueOf(workDewey).doubleValue();
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                // If there is bad formatting of the dewey string, the default value
                // of 0 will be returned.
            }
        }        return result;
    }

    private String normalize(String data) {
        String result = MarcWorxStringHelper.removeChars(data, CHARS_TO_STRIP);
        result = MarcWorxStringHelper.removeExtraWhiteSpace(result);
        result = MarcWorxStringHelper.trimRight((result.toUpperCase()));
        if (MarcWorxStringHelper.isEmpty(result)){
            result = null;
        }
        return result;
    }

    private String normalizePrefix(boolean padCallNumberForUseInUpperLimitQuery) {
        String result = fullPrefix;

        if (result == null){
            result ="";
        }
        if (padCallNumberForUseInUpperLimitQuery) {
            while (result.length() < NORMALIZED_PREFIX_LENGTH ) {
                result += " ";
            }
        }

        if (result.length() > NORMALIZED_PREFIX_LENGTH) {
            result = result.substring(0, NORMALIZED_PREFIX_LENGTH);
        }

        return result;
    }

    private String normalizeDewey(boolean padCallNumberForUseInUpperLimitQuery) {
        String result = null;

        if (dewey == null) {
            result = MarcWorxStringHelper.createString('9', NORMALIZED_DEWEY_LENGTH);
        } else {
            result = dewey;
            if (padCallNumberForUseInUpperLimitQuery && !MarcWorxStringHelper.contains(result, ".")){
                result += "."; 
            }
            String rightPadding = padCallNumberForUseInUpperLimitQuery ? "9" : " ";
            while (result.length() < NORMALIZED_DEWEY_LENGTH) {
                result += rightPadding;
            }
        }

        if (result.length() > NORMALIZED_DEWEY_LENGTH) {
            result = result.substring(0, NORMALIZED_DEWEY_LENGTH);
        }

        return result;
    }

    private String normalizeSuffix(boolean  padCallNumberForUseInUpperLimitQuery) {
        String result = "";

        if(padCallNumberForUseInUpperLimitQuery) {
            result = MarcWorxStringHelper.createString('Z', NORMALIZED_SUFFIX_LENGTH);
        } else if (suffix != null) {
            result = MarcWorxStringHelper.removeExtraWhiteSpace(suffix.toUpperCase());

            // cutter padding must always come before the copy/volume padding
            // we are testing for letter followed by numbers
            result = padNumberFollowing(result, "C.");
            result = padNumberFollowing(result, "V.");
            result = padCutter(result);
        }

        return result;
    }

// -----------------------------------------------------------------------

    /**
     * Find search string and pad the number after it
     */
    private String padNumberFollowing(String value, String search) {
        String result = value;
        int searchIndex;

        /**
         * We'll pull the number out, pad it and then reconstruct the
         * string.
         */
        searchIndex = value.indexOf(search);
        if (searchIndex >= 0) {
            String number = "";
            String localPrefix, localSuffix;
            int numberIndex = searchIndex + search.length();

            // Grab the number
            while ( numberIndex < value.length() &&
                    Character.isDigit(value.charAt(numberIndex))) {
                number += value.charAt(numberIndex);
                numberIndex ++;
            }

            // -1 = because we don't want the last character of the search
            //      string (ex. "C.")
            localPrefix = value.substring(0, searchIndex + search.length() - 1);
            localSuffix = value.substring(searchIndex + search.length() + number.length());

            // Pad it out
            while (number.length() < SUFFIX_COPY_VOLUME_LENGTH) {
                number = "0" + number;
            }

            result = localPrefix + number + localSuffix;
        }

        return result;
    }

// -----------------------------------------------------------------------

    /**
     * Find all cutters and pad it
     */
    private String padCutter(String value) {
        StringBuilder result = new StringBuilder();
        boolean lastCharIsNum = false;
        boolean padNumbers = false;
        int numCount = 0;
        for(int i = 0; i < value.length(); i++) {
            char item = value.charAt(i);

            if (item == '.') {
                numCount = SUFFIX_CUTTER_LENGTH;
            } else if(Character.isDigit(item) ) {
                if (padNumbers){
                    lastCharIsNum = true;
                    numCount++;
                }
            } else {
                if(lastCharIsNum) {
                    while(numCount < SUFFIX_CUTTER_LENGTH) {
                        result.append('0');
                        numCount++;
                    }
                    lastCharIsNum = false;
                    numCount = 0;
                }
                if (!padNumbers){
                    padNumbers = true;
                }
            }
            result.append(item);
        }
        //Pad out the last number
        if(lastCharIsNum) {
            while(numCount < SUFFIX_CUTTER_LENGTH) {
                result.append('0');
                numCount++;
            }
        }

        return result.toString();

    }

    public static boolean requiresSwapping(String callNumRangeFrom, String callNumRangeTo) {
        boolean result = false;

        if (MarcWorxStringHelper.isNotEmpty(callNumRangeFrom) && MarcWorxStringHelper.isNotEmpty(callNumRangeTo)) {
            String sortableFrom = new CallNumber(callNumRangeFrom).getSortableCallNumber();
            String sortableTo = new CallNumber(callNumRangeTo).getSortableCallNumber();
            result = (sortableFrom.compareTo(sortableTo) >= 0);
        }
        return result;
    }

    public BigDecimal getDeweyNumberAsBigDecimal() {
        BigDecimal result = null;

        if (dewey != null) {
            result = new BigDecimal(dewey);
        }
        return result;
    }

    public String getCallNumberPrefix() {
        return prefix;
    }

    public String getDeweyNumber() {
        return dewey;
    }

/*----------------------------------------------------------------------------*/

    public String getNormalizedCallNumber() {
        StringBuilder buffer = new StringBuilder();
        if (MarcWorxStringHelper.isNotEmpty(fullPrefix)) {
            buffer.append(fullPrefix).append(" ");
        }
        if (MarcWorxStringHelper.isNotEmpty(dewey)) {
            buffer.append(dewey).append(" ");
        }
        if (MarcWorxStringHelper.isNotEmpty(suffix)) {
            buffer.append(normalizeSuffix(false));
        }
        return buffer.toString().trim();
    }

/*----------------------------------------------------------------------------*/
    @Override
    public String toString() {
        return "Call Number( normalized prefix:[" + getCallNumberPrefix()+ "], dewey number:[" + getDeweyNumber() + "], suffix:[" + suffix + "], sortable:[" + getSortableCallNumber() + "])";
    }

/*----------------------------------------------------------------------------*/
    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;
        if (obj instanceof CallNumber) {
            retVal = this.getSortableCallNumber().equals( ((CallNumber)obj).getSortableCallNumber() );
        }
        return retVal;
    }

/*----------------------------------------------------------------------------*/
    @Override
    public int hashCode(){
        return MarcWorxObjectHelper.getHashCode(sortableCallNumber);
    }

/*----------------------------------------------------------------------------*/
    public static CallNumber replaceCallNumberPrefix(CallNumber originalCallNumber,
            String newPrefix, boolean addPrefixIfNoneExists) throws MarcException {
        CallNumber newCallNumber = null;

        if (newPrefix == null) {
            throw new MarcException("Call number prefix cannot be null.");
        }
        if (MarcWorxStringHelper.find(newPrefix, " ") >= 0) {
            throw new MarcException("Call number prefix cannot include a space.");
        }
        if (MarcWorxStringHelper.isNotEmpty(new CallNumber(newPrefix).getDeweyNumber())) {
            throw new MarcException("Call number prefix cannot be a Dewey number.");
        }

        String originalPrefix = originalCallNumber.getCallNumberPrefix();
        String workingCallNumber = originalCallNumber.getCallNumber();

        if (MarcWorxStringHelper.isNotEmpty(originalPrefix)) {
            String otherStuff = MarcWorxStringHelper.splitString(workingCallNumber, " ", true, false);

            // If we didn't find a space, the call number is all localPrefix, so just replace everything
            if (otherStuff.length() == workingCallNumber.length()) {

                if (!addPrefixIfNoneExists || MarcWorxStringHelper.isNotEmpty(newPrefix)) {
                    otherStuff = "";
                }
            }

            workingCallNumber = newPrefix + " " + otherStuff.trim();
        } else if (addPrefixIfNoneExists) {
            workingCallNumber = newPrefix + " " + workingCallNumber.trim();
        }

        if (!MarcWorxStringHelper.isEmptyOrWhitespace(workingCallNumber)) {
            workingCallNumber = workingCallNumber.trim();
            newCallNumber = new CallNumber(workingCallNumber);
        }

        return newCallNumber;
    }

/*----------------------------------------------------------------------------*/
    public boolean isDifferent(String origCallNumber, String origSortable,
            String origPrefix, BigDecimal origDewey) {

        if (origCallNumber == null) {
            if (this.getCallNumber() != null) {
                return true;
            }
        } else if (!origCallNumber.equals(this.getCallNumber())) {
            return true;
        }

        if (origSortable == null) {
            if (this.getSortableCallNumber() != null) {
                return true;
            }
        } else if (!origSortable.equals(this.getSortableCallNumber())){
            return true;
        }

        if (origPrefix == null) {
            if (this.getCallNumberPrefix() != null) {
                return true;
            }
        } else if (!origPrefix.equals(this.getCallNumberPrefix())){
            return true;
        }

        if (origDewey == null) {
            if (this.getDeweyNumberAsBigDecimal() != null) {
                return true;
            }
        } else if (!origDewey.equals(this.getDeweyNumberAsBigDecimal())) {
            return true;
        }

        return false;
    }

/*----------------------------------------------------------------------------*/
    public String getCallNumber() {
        return callNumber;
    }


    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }



/*----------------------------------------------------------------------------*/
    private static class CallNumberCompare implements Comparator {

        private static String getSortableCallNumber(String str) {
            CallNumber num = new CallNumber(str);
            return num.getSortableCallNumber();
        }

        @Override
        public int compare(Object o1, Object o2) {
            String str1 = o1 != null ? o1.toString() : "";
            String str2 = o2 != null ? o2.toString() : "";

            return getSortableCallNumber(str1)
                .compareTo(getSortableCallNumber(str2));
        }

    }


}
