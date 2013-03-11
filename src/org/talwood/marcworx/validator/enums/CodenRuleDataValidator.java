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
