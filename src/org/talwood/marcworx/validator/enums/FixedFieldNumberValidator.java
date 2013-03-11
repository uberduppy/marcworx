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
