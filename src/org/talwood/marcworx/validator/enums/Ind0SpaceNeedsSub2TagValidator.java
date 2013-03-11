package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class Ind0SpaceNeedsSub2TagValidator implements BaseMarcRuleValidator {

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
        if(tag.getFirstIndicator() == ' ') {
            if(tag.getSubfields('2').isEmpty()) {
                result = "Subfield \"2\" is required if first indicator is \" \"";
            }
        }
        return result;
    }

}
