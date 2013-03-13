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


public class FixedFieldNumberValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        // Each character must be a space or different from the other entries
        String comment = null;
        boolean passesFilter = true;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            for(int x = 0; x < data.length() && passesFilter; x++) {
                switch(data.charAt(x)) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case ' ':
                    case '-':
                    case 'n':
                    case '|':
                        break;
                    default:
                        passesFilter = false;
                        break;
                }
            }
            if(!passesFilter) {
                comment = "'" + data + "' is not a valid numeric element for " + offsetElement.getDesc();
            }
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

}
