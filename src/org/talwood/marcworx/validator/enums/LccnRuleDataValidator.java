package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.stdnumbers.LibCongressControlNumber;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class LccnRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            LibCongressControlNumber lData = new LibCongressControlNumber(data);
            if(!lData.isValid()) {
                message = "LCCN \"" + data + "\" " + lData.getErrNo().getMessage();
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
