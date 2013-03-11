package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class SubINeedsInd18TagValidator implements BaseMarcRuleValidator {

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
        if(tag.getSubfields('i').size() > 0) {
            if(tag.getSecondIndicator() != '8') {
                result = "Second indicator of \"8\" is required if subfield \"i\" is present";
            }
        }
        return result;
    }

}
