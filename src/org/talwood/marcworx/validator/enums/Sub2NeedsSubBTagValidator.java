package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class Sub2NeedsSubBTagValidator implements BaseMarcRuleValidator {

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
        if(tag.getSubfields('2').size() > 0) {
            if(tag.getSubfields('b').isEmpty()) {
                result = "Subfield \"b\" is required if subfield \"2\" exists";
            }
        }
        return result;
    }

}
