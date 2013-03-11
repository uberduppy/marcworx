package org.talwood.marcworx.validator.enums;

import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;


public interface BaseMarcRuleValidator {
   public String validateFixedData(String data, MarcFixedLengthOffsetValue offsetElement);
   public String validateData(String data);
   public String validateTag(MarcTag tag);
}
