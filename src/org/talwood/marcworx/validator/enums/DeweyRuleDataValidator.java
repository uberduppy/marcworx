package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.stdnumbers.CallNumber;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class DeweyRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            CallNumber cn = new CallNumber(data);
            if(MarcWorxStringHelper.isEmpty(cn.getDeweyNumber())) {
                message = "Call number \"" + data + "\" is not a valid Dewey";
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
