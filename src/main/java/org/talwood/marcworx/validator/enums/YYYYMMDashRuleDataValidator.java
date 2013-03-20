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

public class YYYYMMDashRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isEmpty(data) || data.length() != 6) {
            message = "A valid date in YYYYMM format is required";
        } else {
            String year = data.substring(0, 4);
            String month = data.substring(4, 6);
            if(MarcWorxStringHelper.contains(month, "-")) {
                month = "01";
            }
            try {
                int y = Integer.parseInt(year);
                int m = Integer.parseInt(month);
                if(y < 1000 || m < 1 || m > 12) {
                    message = "A valid date in YYYYMM format is required";
                }
            } catch (NumberFormatException ex) {
                message = "A valid date in YYYYMM format is required";
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
