
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
