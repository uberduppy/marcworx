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

import java.util.Calendar;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

/**
 *
 * @author twalker
 */
public class YYYYMMDDRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isEmpty(data) || data.length() < 8) {
            message = "A valid date in YYYYMMDD format is required";
        } else {
            String year = data.substring(0, 4);
            String month = data.substring(4, 6);
            String day = data.substring(6, 8);
            try {
                int y = Integer.parseInt(year);
                int m = Integer.parseInt(month);
                int d = Integer.parseInt(day);
                if(y < 1000 || m < 1 || m > 12 || d < 1 || d > 31) {
                    message = "A valid date in YYYYMMDD format is required";
                } else {
                    int maxDay = 31;
                    switch(m - 1) {
                        case Calendar.FEBRUARY:
                            maxDay = 28;
                            if(y % 4 == 0 && y % 100 != 0) {
                                maxDay = 29;
                            }
                            break;
                        case Calendar.APRIL:
                        case Calendar.JUNE:
                        case Calendar.SEPTEMBER:
                        case Calendar.NOVEMBER:
                            maxDay = 30;
                            break;
                        default:
                            // All other months
                            break;
                    }
                    if(d > maxDay) {
                        message = "A valid date in YYYYMMDD format is required";
                    }
                }
            } catch (NumberFormatException ex) {
                message = "A valid date in YYYYMMDD format is required";
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
