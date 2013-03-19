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
package org.talwood.marcworx.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MarcWorxStringHelper {
    public final static char DECIMAL_POINT = 0x002E;

    public static String prepend(String source, int length, char fillChar) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + source.length() < length) {
            sb.append(fillChar);
        }
        sb.append(source);

        return sb.toString();
    }

    public static int length(String source) {
        int result = 0;
        if(isNotEmpty(source)) {
            result = source.length();
        }
        return result;
    }

    public static String replaceStringWithString(String input, String oldString, String newString) {
        StringBuilder result = new StringBuilder();

        if (input != null) {
            int p = 0;
            int i = input.indexOf(oldString, p);
            while (i >= 0) {
                result.append(input.substring(p, i)).append(newString);
                p = i + oldString.length();
                i = input.indexOf(oldString, p);
            }
            result.append(input.substring(p));
        }

        return result.toString();
    }

    public static String concatenate(String start, String end, String separator) {
        StringBuilder sb = new StringBuilder();
        if(isNotEmpty(start)) {
            sb.append(start);
        }
        if(isNotEmpty(end)) {
            if(isNotEmpty(start)) {
                sb.append(separator);
            }
            sb.append(end);
        }
        return sb.toString();
    }
    
    public static String removeHTMLTags(String buffer) {
        StringBuilder result = new StringBuilder();
        boolean inHtmlTag = false;
        for (int x = 0; x < buffer.length(); x++) {
            if (buffer.charAt(x) == '<') {
                inHtmlTag = true;
            }
            if (!inHtmlTag) {
                result.append(buffer.charAt(x));
            }
            if (buffer.charAt(x) == '>') {
                inHtmlTag = false;
            }
        }
        return result.toString();
    }

    public static String removeChars(String value, char removeChar) {
        return removeChars(value, String.valueOf(removeChar));
    }

    public static String removeChars(String value, String removeChars) {
        String result = "";

        if (!isEmpty(value) && !isEmpty(removeChars)) {

            StringBuilder filteredBuffer = new StringBuilder(value.length());
            for (int i = 0; i < value.length(); i++) {
                if (!contains(removeChars, String.valueOf(value.charAt(i)))) {
                    filteredBuffer.append(value.charAt(i));
                }
            }
            result = filteredBuffer.toString();
        }

        return result;
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean contains(String sourceString, String findString) {
        return (contains(sourceString, findString, true));
    }

    public static boolean contains(String sourceString, char findChar) {
        return (contains(sourceString, String.valueOf(findChar), true));
    }

    public static boolean contains(String sourceString, byte findChar) {
        return (contains(sourceString, String.valueOf(findChar), true));
    }

    public static boolean contains(String sourceString, String findString,
            boolean caseSensitive) {

        if (sourceString == null || findString == null) {
            return false;
        }
        if (!caseSensitive) {
            sourceString = sourceString.toLowerCase();
            findString = findString.toLowerCase();
        }

        return (sourceString.contains(findString));
    }

    static public String rightMost(String sourceString, int count) {
        String result = "";

        if (!isEmpty(sourceString)) {
            int sourceLen = sourceString.length();
            int targetLen = (sourceLen > count) ? count : sourceLen;

            char[] buffer = new char[targetLen];

            sourceString.getChars(sourceLen - targetLen, sourceLen, buffer, 0);
            result = new String(buffer);
        }
        return result;
    }

    public static String leftMost(String sourceString, int count) {
        String result = "";

        if (!isEmpty(sourceString)) {
            int sourceLen = sourceString.length();
            int targetLen = (sourceLen > count) ? count : sourceLen;

            char[] buffer = new char[targetLen];

            sourceString.getChars(0, targetLen, buffer, 0);
            result = new String(buffer);
        }
        return result;
    }

    public static String trimEnds(String sourceString) {
        return trimLeft(trimRight(sourceString));
    }

    public static String trimEnds(String sourceString, String trimChars) {
        return trimLeft(trimRight(sourceString, trimChars), trimChars);
    }

    public static String trimEnds(String sourceString, char removeChar) {
        return trimRight(trimLeft(sourceString, removeChar), removeChar);
    }

    public static String trimLeft(String sourceString) {
        String result = "";

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);
            int length = buffer.length();

            while ((length != 0)
                    && (Character.isWhitespace(buffer.charAt(0)))) {
                buffer.deleteCharAt(0);
                length--;
            }

            result = buffer.toString();
        }

        return result;
    }

    public static String trimLeft(String sourceString, char removeChar) {
        String result = "";

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);

            while ((buffer.length() > 0) && buffer.charAt(0) == removeChar) {
                buffer.deleteCharAt(0);
            }
            result = buffer.toString();
        }

        return result;
    }

    public static String trimLeft(String sourceString, String removeChars) {
        String result = "";
        int length = removeChars.length();

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);
            boolean done = false;

            while ((buffer.length() > 0) && !done) {
                done = true;
                for (int i = 0; i < length; i++) {
                    if (buffer.charAt(0) == removeChars.charAt(i)) {
                        buffer.deleteCharAt(0);
                        done = false;
                        break;
                    }
                }
            }
            result = buffer.toString();
        }

        return result;
    }

    public static String trimRight(String sourceString, String listOfCharsToRemove) {
        String result = "";
        int length = listOfCharsToRemove.length();

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);
            boolean done = false;

            while ((buffer.length() > 0) && !done) {
                int offset = buffer.length() - 1;
                done = true;
                for (int i = 0; i < length; i++) {
                    if (buffer.charAt(offset) == listOfCharsToRemove.charAt(i)) {
                        buffer.deleteCharAt(offset);
                        done = false;
                        break;
                    }
                }
            }
            result = buffer.toString();
        }

        return result;
    }

    public static String trimRight(String sourceString) {
        String result = "";

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);
            int pos = buffer.length() - 1;

            while ((pos >= 0) && Character.isWhitespace(buffer.charAt(pos))) {
                buffer.deleteCharAt(pos--);
            }
            result = buffer.toString();
        }

        return result;
    }

    public static String trimRight(String sourceString, char removeChar) {
        String result = "";

        if (!isEmpty(sourceString)) {
            StringBuilder buffer = new StringBuilder(sourceString);
            int length = buffer.length();

            while ((length > 0) && buffer.charAt(length - 1) == removeChar) {
                buffer.deleteCharAt(length - 1);
                length = buffer.length();
            }

            result = buffer.toString();
        }
        return result;
    }

    public static String stackTraceToString(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement el = stackTrace[i];
            sb.append("    ").append(el.toString()).append("\n");
        }
        return sb.toString();
    }

    public static String stackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static int find(String sourceString, String findString) {
        return find(sourceString, findString, true);
    }

    public static int find(String sourceString, String findString, boolean caseSensitive) {
        return find(sourceString, findString, caseSensitive, 0);
    }

    public static int find(String sourceString, String findString, boolean caseSensitive,
            int offset) {
        int retVal = -1;

        if (!isEmpty(sourceString) && !isEmpty(findString)) {
            try {
                if (!caseSensitive) {
                    retVal = sourceString.toLowerCase().indexOf(findString.toLowerCase(), offset);
                } else {
                    retVal = sourceString.indexOf(findString, offset);
                }
            } catch (java.lang.StringIndexOutOfBoundsException ex) {
                // nothing to do!!!
            }
        }

        return retVal;
    }

    public static String splitString(String stringToSplit, String splitCriteria,
            boolean includeCriteria, boolean returnDataBeforeCriteria) {
        String result = "";

        if (!isEmpty(stringToSplit) && !isEmpty(splitCriteria)) {
            //Set some stuff up for later use
            int position = stringToSplit.indexOf(splitCriteria);
            int criteriaLength = splitCriteria.length();

            if (position >= 0) {
                //Found the splitCriteria
                if (returnDataBeforeCriteria == true) {
                    //If you want to get all the data BEFORE splitCriteria
                    if (includeCriteria == true) {
                        result = stringToSplit.substring(0, position + criteriaLength);
                    } else {
                        result = stringToSplit.substring(0, position);
                    }
                } else {
                    //If you want to get all the data AFTER splitCriteria
                    if (includeCriteria == true) {
                        result = stringToSplit.substring(position);
                    } else {
                        result = stringToSplit.substring(position + criteriaLength);
                    }
                }
            } else {
                //If could NOT find splitCritera within stringToSplit, return
                //whole string as it was
                result = stringToSplit;
            }
        }

        return result;
    }

    public static boolean isEmptyOrWhitespace(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        return str.equals("");
    }

    public static String leftJustify(String sourceString, int targetLen, char pad)
            throws IndexOutOfBoundsException {
        int sourceLen = sourceString.length();
        char[] buffer = new char[targetLen];

        if (targetLen < sourceLen) {
            // Just truncate
            sourceString.getChars(0, targetLen, buffer, 0);
        } else {
            int i = 0;

            // Add String
            while (i < sourceLen) {
                buffer[i] = sourceString.charAt(i++);
            }
            // Pad Right
            while (i < targetLen) {
                buffer[i++] = pad;
            }
        }
        return new String(buffer);
    }

    public static String truncate(String src, int maxChars, boolean addEllipses) {
        if (src != null) {
            if (src.length() > maxChars) {
                StringBuilder result = new StringBuilder(src.substring(0, maxChars));
                if (addEllipses && maxChars < src.length()) {
                    result.append("...");
                }
                return result.toString();
            }
        }
        return src;
    }

    public static String removeExtraWhiteSpace(String workString) {

        // handle null or empty strings - nothing to do
        if (isEmpty(workString)) {
            return workString;
        }

        StringBuilder sb = new StringBuilder();

        int wsCount = 0;

        for (int x = 0; x < workString.length(); x++) {
            char ch = workString.charAt(x);

            if (Character.isWhitespace(ch)) {
                if (wsCount == 0) {
                    sb.append(' ');
                }
                wsCount++;
            } else {  // non-white, kick it, reset count
                wsCount = 0;
                sb.append(ch);
            }
        }
        return sb.toString();
    }

     public static String createString(char ch, int count) {
        StringBuilder result = new StringBuilder(count);

        for (int index = 0; index < count; index ++) {
            result.append(ch);
        }

        return result.toString();
     }

     public static String removeChars(String source, int offset, int numberToRemove) {
         StringBuilder sb = new StringBuilder();
         if(!isEmpty(source)) {
            for(int x = 0; x < source.length(); x++) {
                if(x < offset || x >= (offset + numberToRemove)) {
                    sb.append(source.charAt(x));
                }
            }
         }
         return sb.toString();
     }

     public static String substring(String source, int offset, int dataLength) {
         StringBuilder sb = new StringBuilder();
         if(!isEmpty(source) && source.length() >= offset + dataLength) {
            sb.append(source.substring(offset, offset + dataLength));
         }
         return sb.toString();
     }

     public static boolean hasAtLeastNCharacters(String source, int minimumLength) {
         boolean result = true;
         if(isEmpty(source) || source.length() < minimumLength) {
             result = false;
         }
         return result;
     }

     public static boolean startsWith(String source, String target, boolean caseSensitive) {
         boolean result = false;
         if(!isEmpty(source) && !isEmpty(target) && source.length() >= target.length()) {
             if(caseSensitive) {
                 result = target.equals(source.substring(0, target.length()));
             } else {
                 result = target.equalsIgnoreCase(source.substring(0, target.length()));
             }
         }
         return result;
     }

     public static boolean endsWith(String source, String target, boolean caseSensitive) {
         boolean result = false;
         if(!isEmpty(source) && !isEmpty(target) && source.length() >= target.length()) {
             if(caseSensitive) {
                 result = target.equals(source.substring(source.length() - target.length(), source.length()));
             } else {
                 result = target.equalsIgnoreCase(source.substring(source.length() - target.length(), source.length()));
             }
         }
         return result;
     }

     public static boolean equals(String source, String target, boolean caseSensitive) {
         boolean result = false;
         if(isEmpty(source) && isEmpty(target)) {
             result = true;
         } else if(!isEmpty(source) && !isEmpty(target)) {
             if(caseSensitive) {
                 result = target.equals(source);
             } else {
                 result = target.equalsIgnoreCase(source);
             }
         }
         return result;
     }

    public static int countNumberOfOccurances(String sourceString, String searchText) {
        int idx = 0;
        int count = 0;

        // Short-circuit if parameter is empty or null
        boolean done = isEmpty(sourceString);
        while (!done) {
            idx = find(sourceString, searchText, false, idx);
            if(idx != -1) {
                idx++;
                count++;
            } else {
                done = true;
            }
        }
        return count;
    }
    
    public static boolean needsCData(String data) {
        boolean result = false;
        for(int x = 0; x < data.length(); x++) {
            char c = data.charAt(x);
            if(c == '>' || c == '<' || c == '&' || (int)c > 127) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public static String normalizeSpace(String source) {
        if(isNotEmpty(source)) {
            source = source.trim();
            while(source.indexOf("  ") != -1) {
                source = replaceStringWithString(source, "  ", " ");
            }
        }
        return source;
    }

    public static String convertListToString(List<String> myList) {
        return convertListToStringWithSeparator(myList, " ");
    }

    /*----------------------------------------------------------------------------*/

    public static String convertListToStringWithSeparator(List<String> myList, String separator) {
        StringBuilder result = new StringBuilder();

        Iterator<String> iter = myList.iterator();
        while (iter.hasNext()) {
            String item = iter.next();

            // append each list item to the result string
            result.append(item);

            // do this only if we have more
            if (iter.hasNext()) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    public static String formatOnlyLettersNumbersAndSpaces(String keyword) {
        StringBuilder result = new StringBuilder();
        if(!isEmpty(keyword)) {
            for (int i = 0; i < keyword.length(); i++) {
                char ch = keyword.charAt(i);
                if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
                    result.append(ch);
                }
            }

        }
        return result.toString();
    }

    public static String getSubString(String data, int index, int length) {
        String sub = null;
        if(isNotEmpty(data)) {
            try {
                sub = data.substring(index, index + length);
            }
            catch (java.lang.StringIndexOutOfBoundsException ex) {
                // we return null.
            }
        }

        return sub;
    }

    public static String makeSortKey( String text, int maxLength ) {
        String sorted = makeSortKeyWithMaxPadding(text, 10);
        sorted = truncate(sorted, maxLength, false);
        return sorted;
    }
    
    public static String makeSortKeyWithMaxPadding( String text, int padding ) {
        String sorted = "";

        if (text != null) {
            sorted = Normalizer.normalize(text, Normalizer.Form.NFKD);
        }

        StringBuilder result = new StringBuilder();
        StringBuilder numeric = new StringBuilder();

        if (!isEmpty(text)) {
            int length = sorted.length();
            int index = 0;
            while (index < length) {
                char ch = sorted.charAt(index++);

                if (ch == '-') {
                    result.append(' ');
                }
                else {
                    if (Character.isDigit(ch)) {
                        int padLength = 0;
                        numeric.setLength(0);
                        numeric.append(ch);

                        boolean numericMode = true;
                        while((numericMode) && (index < length)) {
                            ch = sorted.charAt(index++);

                            if (ch == '.') {
                                padLength = padding - numeric.length();
                            }
                            else if (Character.isDigit(ch)) {
                                numeric.append(ch);
                            }
                            else {
                                numericMode = false;
                                index--;
                            }

                        }

                        if (padLength == 0) {
                            padLength = padding - numeric.length();
                        }
                        while (padLength > 0) {
                            result.append('0');
                            padLength--;
                        }
                        result.append(numeric.toString());
                    }
                    else if (ch != '.') {
                        result.append(ch);
                    }
                }
            }
        }

        // Now remove the punctuation
        String value = result.toString().toUpperCase();
        value = removePunctuation(value);
        return removeExtraWhiteSpace(value).trim();

    }
    
    public static String removePunctuation(String value) {
          return removePunctuation(value, null);
    }
    
    
    public static String removePunctuation(String value, char exceptions[]) {
        if (value == null) {
            return "";
        }

        if (exceptions != null) {
            // Must not assume we can modify the character array passed into us
            // must make a copy....
            char copy[] = new char[exceptions.length];
            System.arraycopy(exceptions, 0, copy, 0, exceptions.length);
            exceptions = copy;
            Arrays.sort(exceptions);
        }

        boolean exception = false;
        StringBuilder filteredBuffer = new StringBuilder(value.length());

        for (int i = 0; i < value.length(); i++) {
            char testChar = value.charAt(i);

            if (exceptions != null) {
                exception = (Arrays.binarySearch(exceptions, testChar) >= 0);
            }

            if (exception || Character.isLetterOrDigit(testChar) || testChar == DECIMAL_POINT || Character.isWhitespace(testChar)) {
                filteredBuffer.append(testChar);
            }
        }
        return filteredBuffer.toString();
    }
    
    public static String findStringBetweenString(String source, String startString, String endString) {
        String result = null;

        if (source != null && startString != null && endString != null) {
            int firstString = source.indexOf(startString);
            if (firstString >= 0) {
                int secondString = source.indexOf(endString, firstString + startString.length());
                if (secondString >= 0) {
                    result = source.substring(firstString + startString.length(), secondString);
                }
            }
        }

        return result;
    }
    
    public static int getLength(String source) {
        int result = 0;
        if(source != null) {
            result = source.length();
        }
        return result;
    }

    public static int reverseFindIndexOfAny(String sourceString, String findCriteria,
                            int startPos) {
        int result = -1;

        if ((isNotEmpty(sourceString)) && (isNotEmpty(findCriteria))) {

            for (int x = 0; x < findCriteria.length(); x++ ) {
                //Now that I have my char, test it
                int foundIndex = sourceString.lastIndexOf(findCriteria.charAt(x), startPos);

                //If this is the first one, use it.
                if (foundIndex > result) {
                    result = foundIndex;
                }
            }
        }

        return result;

    }
    
    public static String replaceAll( String strOrg, String strFrom,
        String strTo ) {
        return replaceAll(strOrg, strFrom, strTo, true);
    }

    public static String replaceAll( String strOrg, String strFrom,
        String strTo, boolean caseSensitive )
        {
        StringBuilder result = new StringBuilder();
        int prev = 0, cur, len = strOrg.length(), fromlen = strFrom.length();
        while (prev < len) {
            if (caseSensitive) {
                cur = strOrg.indexOf(strFrom, prev);
            } else {
                cur = find(strOrg, strFrom, false, prev);
            }
            if (cur >= 0) {
                result.append(strOrg.substring(prev, cur)).append(strTo);
                prev = cur + fromlen;
            } else {
                break;
            }
        }
        if (prev < len) {
            result.append(strOrg.substring(prev));
        }
        return( result.toString() );
    }
    
    public static String stripTrailingData(String source, String charsToStrip) {
        if(isNotEmpty(source) && isNotEmpty(charsToStrip)) {
            StringBuilder sb = new StringBuilder(source);
            while(sb.length() > 0 && charsToStrip.indexOf(sb.charAt(sb.length() - 1)) >= 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            source = sb.toString();
        }
        return source;
    }
    
    public static String stripLeadingData(String source, String charsToStrip) {
        if(isNotEmpty(source) && isNotEmpty(charsToStrip)) {
            StringBuilder sb = new StringBuilder(source);
            while(sb.length() > 0 && charsToStrip.indexOf(sb.charAt(0)) >= 0) {
                sb.deleteCharAt(0);
            }
            source = sb.toString();
        }
        return source;
    }
    
}
