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
