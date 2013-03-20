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
package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class FixedFieldYYYYValidator  implements BaseMarcRuleValidator {

    private static final String[] validMasks = {"    ", "||||", "####", "uuuu", "#uuu", "##uu", "###u"};

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        // Each character must be a space or different from the other entries
        String comment = null;
        String date = convertDigitsToPounds(data);
        if (date != null && date.length() == 4) {
            if(!validateMask(date)) {
                comment = "Invalid Date for " + offsetElement.getDesc();
            }
        } else {
            comment = "Invalid number of digits in date " + offsetElement.getDesc();
        }
        return comment;
    }

    @Override
    public String validateData(String data) {
        return null;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

    private String convertDigitsToPounds(String source) {
        String retVal = null;
        if(source != null) {
            char[] sourceChars = source.toCharArray();
            for(int x = 0; x < sourceChars.length; x++) {
                if(Character.isDigit(sourceChars[x])) {
                    sourceChars[x] = '#';
                }
            }
            retVal = new String(sourceChars);
        }
        return retVal;
    }

    private boolean validateMask(String date) {
        boolean isValid = false;
        if(date != null) {
            for(int x = 0; x < validMasks.length; x++) {
                if(date.equals(validMasks[x])) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

}
