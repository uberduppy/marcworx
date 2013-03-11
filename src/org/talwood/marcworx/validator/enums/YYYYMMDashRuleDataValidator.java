package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class YYYYMMDashRuleDataValidator implements BaseMarcRuleValidator {

    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        return null;
    }

    @Override
    public String validateData(String data) {
        String message = null;
        if(MarcWorxStringHelper.isEmpty(data) || data.length() != 6) {
            message = "A valid date in YYYYMM format is required";
        } else {
            String year = data.substring(0, 4);
            String month = data.substring(4, 6);
            if(MarcWorxStringHelper.contains(month, "-")) {
                month = "01";
            }
            try {
                int y = Integer.parseInt(year);
                int m = Integer.parseInt(month);
                if(y < 1000 || m < 1 || m > 12) {
                    message = "A valid date in YYYYMM format is required";
                }
            } catch (NumberFormatException ex) {
                message = "A valid date in YYYYMM format is required";
            }
        }
        return message;
    }

    @Override
    public String validateTag(MarcTag tag) {
        return null;
    }

}
