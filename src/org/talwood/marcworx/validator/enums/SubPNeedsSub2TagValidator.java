package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class SubPNeedsSub2TagValidator implements BaseMarcRuleValidator {

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
        if(tag.getSubfields('p').size() > 0) {
            if(tag.getSubfields('2').isEmpty()) {
                result = "Subfield \"2\" is required if subfield \"p\" exists";
            }
        }
        return result;
    }

}
