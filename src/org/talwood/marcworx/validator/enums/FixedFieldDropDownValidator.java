package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthCodeValue;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;


public class FixedFieldDropDownValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        // Each character must be a space or different from the other entries
        String comment = null;
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            MarcFixedLengthCodeValue e = offsetElement.findByCode(data);
            if(e == null) {
                comment = "'" + data + "' is not a valid element for " + offsetElement.getDesc();
            } else if (e.isObsolete()) {
                comment = "'" + data + "' was made obsolete in " + e.getObsoletewhen() + " for " + offsetElement.getDesc();
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
