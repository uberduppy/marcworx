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

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class CodenRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        // 6 characters, all digits or uppercase...
        String message = null;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            String newData = MarcWorxStringHelper.trimEnds(data);
            if(newData.length() != 6) {
                message = "CODEN identifier must be 6 characters long and consist of uppercase letters and/or numbers";
            } else {
                for(int x = 0; x < newData.length(); x++) {
                    char thisChar = newData.charAt(x);
                    if(!(Character.isUpperCase(thisChar) || Character.isDigit(thisChar))) {
                        message = "CODEN identifier must be 6 characters long and consist of uppercase letters and/or numbers";
                        break;
                    }
                }
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
