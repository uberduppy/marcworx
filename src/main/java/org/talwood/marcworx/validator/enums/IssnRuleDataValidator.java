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
import org.talwood.marcworx.marc.stdnumbers.IntStdSerialNumber;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;


public class IssnRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            IntStdSerialNumber lData = new IntStdSerialNumber(data);
            if(!lData.isValid()) {
                message = "ISSN \"" + data + "\" " + lData.getErrNo().getMessage();
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
