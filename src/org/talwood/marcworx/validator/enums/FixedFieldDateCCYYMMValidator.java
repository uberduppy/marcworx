package org.talwood.marcworx.validator.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;

public class FixedFieldDateCCYYMMValidator implements BaseMarcRuleValidator {
    @Override
    public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement) {
        // Each character must be a space or different from the other entries
        boolean result = false;

        String comment = null;
        if (data != null && data.trim().length() == 6) {
            result = true;
            // Make sure that the date has the following format yymmdd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            dateFormat.setLenient(false);
            try {
                if(!("||||||".equals(data))) {
                    dateFormat.parse(data);
                }
            } catch (ParseException ex) {
                for (int i = 0; i < 4; i++) {
                    if ( !Character.isDigit(data.charAt(i)) && data.charAt(i) != '-' ) {
                        result = false;
                    }
                }
                switch ( data.charAt(4) ) {

                     case '-':
                     case '0':
                        result = "0123456789-".indexOf(data.charAt(5)) != -1;
                        break;

                     case '1':
                        result = "012-".indexOf(data.charAt(5)) != -1;
                        break;

                     default:
                        result = false;
                        break;
                }
                if ( !result ) {
                    comment = "Invalid date format for " + offsetElement.getDesc();
                }
            }
        } else {
            comment = "Invalid number of digits in date for " + offsetElement.getDesc();
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
