package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthCodeValue;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;


public class FixedFieldCheckBoxValidator implements BaseMarcRuleValidator {
    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        String comment = null;
        String foundData = "";
        if(MarcWorxStringHelper.isNotEmpty(data)) {
            for(int x = 0; x < data.length() && MarcWorxStringHelper.isEmpty(comment); x++) {
                if(data.charAt(x) != ' ') {
                    if(foundData.indexOf(String.valueOf(data.charAt(x))) != -1) {
                        comment = offsetElement.getDesc() + " contains " + data.charAt(x) + " more than once.";
                    } else {
                        MarcFixedLengthCodeValue e = offsetElement.findByCode(String.valueOf(data.charAt(x)));
                        if(e == null) {
                            comment = "'" + data.charAt(x) + "' is not a valid element for " + offsetElement.getDesc();
                        } else if (e.isObsolete()) {
                            comment = "'" + data.charAt(x) + "' was made obsolete in " + e.getObsoletewhen() + " for " + offsetElement.getDesc();
                        }
                    }
                    foundData = foundData + data.charAt(x);
                }
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
