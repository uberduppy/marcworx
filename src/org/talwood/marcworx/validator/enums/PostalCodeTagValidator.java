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
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class PostalCodeTagValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        return null;
    }

    @Override
    public String validateTag(MarcTag tag) {
        String result = null;
        MarcSubfield subA = tag.getSubfield('a', 1);
        MarcSubfield subB = tag.getSubfield('b', 1);
        if(subB != null) {
            if(subA == null) {
                // sub A is required
                result = "Subfield \"a\" is required if subfield \"b\" exists";
            } else if ("USPS".equals(subB.getData())) {
                // USPS required 6 digits.
                if(!checkForDigits(subA.getData(), 6)) {
                    result = "Subfield \"a\" must contain 6 digits for \"USPS\" code";
                }
            } else if ("CP".equals(subB.getData())) {
                // CP required 4 digits.
                if(!checkForDigits(subA.getData(), 4)) {
                    result = "Subfield \"a\" must contain 4 digits for \"CP\" code";
                }
            } else {
                // Invalid code is subfield b
                result = "Subfield \"b\" must contain \"USPS\" or \"CP\"";
            }
        }
        return result;
    }

    private boolean checkForDigits(String data, int length) {
        boolean result = true;
        if(MarcWorxStringHelper.isNotEmpty(data) && data.length() == length) {
            for(int x = 0; x < data.length(); x++) {
                if(!Character.isDigit(data.charAt(x))) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }

        return result;
    }

}
