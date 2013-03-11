package org.talwood.marcworx.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.validator.constants.MarcLimitersConstants;
import org.talwood.marcworx.validator.elements.MarcFixedLengthDataValue;
import org.talwood.marcworx.validator.elements.MarcFixedLengthOffsetValue;
import org.talwood.marcworx.validator.elements.MarcLineEntryValue;
import org.talwood.marcworx.validator.elements.MarcTagValue;
import org.talwood.marcworx.validator.elements.MarcValidatorValue;
import org.talwood.marcworx.validator.enums.MarcValidatorEnum;
import org.talwood.marcworx.validator.results.MarcValidatorResultRow;
import org.talwood.marcworx.validator.results.MarcValidatorResultType;

public class MarcValidatorProcessor {

    public static List<MarcValidatorResultRow> validateRecord(MarcValidatorValue value, MarcRecord marc, boolean notifyOfLocalTag, int displayFlag) {
        List<MarcValidatorResultRow> result = new ArrayList<MarcValidatorResultRow>();
        // First, get all nonrepeatabletags and see if there are more than one in the MARC.
        Iterator<MarcTagValue> nonRptIter = value.getNonRepeatTagList().iterator();
        if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_NR_TAGS)) {
            while(nonRptIter.hasNext()) {
                MarcTagValue nr = nonRptIter.next();
                if(marc.getAllTags(nr.getTagNumber()).size() > 1) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.NONREPEATABLE_TAG, nr.getTagNumber()));
                }
            }
        }

        // Process the leader!
        MarcLeader leader = marc.getLeader();
        Iterator<MarcFixedLengthOffsetValue> ldrIter = value.getLeaderList().get(0).getOffsetElementList().iterator();
        while(ldrIter.hasNext()) {
            MarcFixedLengthOffsetValue mrfloe = ldrIter.next();
            MarcValidatorEnum e = MarcValidatorEnum.findByValidatorName(mrfloe.getValidator());
            String valResults = e.getValidator().validateFixedData(MarcWorxStringHelper.substring(leader.getCurrentLeaderData(), mrfloe.getPosition(), mrfloe.getLength()), mrfloe);
            if(MarcWorxStringHelper.isNotEmpty(valResults)) {
                result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_FIXED_DATA, valResults, MarcTagConstants.LEADER));
            }
        }

        // Process all 006 tags.
        Iterator<MarcTag> tag006Iter = marc.getAllTags(6).iterator();
        while(tag006Iter.hasNext()) {
            MarcTag thisTag = tag006Iter.next();
            if(MarcWorxStringHelper.isNotEmpty(thisTag.getCurrentTagData())) {
                MarcFixedLengthDataValue mrflde = value.findDataElementByTagAndCode(6, thisTag.getCurrentTagData().charAt(0));
                if(mrflde == null || (mrflde.getDatalength() > 0 && mrflde.getDatalength() != thisTag.getCurrentTagData().length())) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_006_LENGTH, thisTag.getTagNumber()));
                } else {
                    Iterator<MarcFixedLengthOffsetValue> iter = mrflde.getOffsetElementList().iterator();
                    while(iter.hasNext()) {
                        MarcFixedLengthOffsetValue mrfloe = iter.next();
                        MarcValidatorEnum e = MarcValidatorEnum.findByValidatorName(mrfloe.getValidator());
                        String valResults = e.getValidator().validateFixedData(MarcWorxStringHelper.substring(thisTag.getCurrentTagData(), mrfloe.getPosition(), mrfloe.getLength()), mrfloe);
                        if(MarcWorxStringHelper.isNotEmpty(valResults)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_FIXED_DATA, valResults, thisTag.getTagNumber()));
                        }
                    }
                }
            }
        }
        // Process all 007 tags.
        Iterator<MarcTag> tag007Iter = marc.getAllTags(7).iterator();
        while(tag007Iter.hasNext()) {
            MarcTag thisTag = tag007Iter.next();
            if(MarcWorxStringHelper.isNotEmpty(thisTag.getCurrentTagData())) {
                MarcFixedLengthDataValue mrflde = value.findDataElementByTagAndCode(7, thisTag.getCurrentTagData().charAt(0));
                if(mrflde == null || (mrflde.getDatalength() > 0 && mrflde.getDatalength() != thisTag.getCurrentTagData().length())) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_007_LENGTH, mrflde.getDatalength()));
                } else {
                    Iterator<MarcFixedLengthOffsetValue> iter = mrflde.getOffsetElementList().iterator();
                    while(iter.hasNext()) {
                        MarcFixedLengthOffsetValue mrfloe = iter.next();
                        MarcValidatorEnum e = MarcValidatorEnum.findByValidatorName(mrfloe.getValidator());
                        String valResults = e.getValidator().validateFixedData(MarcWorxStringHelper.substring(thisTag.getCurrentTagData(), mrfloe.getPosition(), mrfloe.getLength()), mrfloe);
                        if(MarcWorxStringHelper.isNotEmpty(valResults)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_FIXED_DATA, valResults, thisTag.getTagNumber()));
                        }
                    }
                }
            }
        }

        // Process all 008 tags.
        Iterator<MarcTag> tag008Iter = marc.getAllTags(8).iterator();
        while(tag008Iter.hasNext()) {
            MarcTag thisTag = tag008Iter.next();
            if(MarcWorxStringHelper.isNotEmpty(thisTag.getCurrentTagData())) {
                MarcFixedLengthDataValue mrflde = value.findDataElementByTagAndCode(8, leader.getRecordTypeEnum().getCode());
                if(mrflde == null || (mrflde.getDatalength() > 0 && mrflde.getDatalength() != thisTag.getCurrentTagData().length())) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_008_LENGTH, thisTag.getTagNumber()));
                } else {
                    Iterator<MarcFixedLengthOffsetValue> iter = mrflde.getOffsetElementList().iterator();
                    while(iter.hasNext()) {
                        MarcFixedLengthOffsetValue mrfloe = iter.next();
                        MarcValidatorEnum e = MarcValidatorEnum.findByValidatorName(mrfloe.getValidator());
                        String valResults = e.getValidator().validateFixedData(MarcWorxStringHelper.substring(thisTag.getCurrentTagData(), mrfloe.getPosition(), mrfloe.getLength()), mrfloe);
                        if(MarcWorxStringHelper.isNotEmpty(valResults)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_FIXED_DATA, valResults, thisTag.getTagNumber()));
                        }
                    }
                }
            }
        }

        for(int x = 10; x < 1000; x++) {
            Iterator<MarcTag> tags = marc.getAllTags(x).iterator();
            while(tags.hasNext()) {
                MarcTag thisTag = tags.next();
                if (checkLocalTag(thisTag.getTagNumber())) {
                    if(notifyOfLocalTag) {
                        result.add(new MarcValidatorResultRow(MarcValidatorResultType.LOCAL_TAGNUM, thisTag.getTagNumber()));
                    }
                } else {
                    MarcTagValue mrte = value.findTagElement(thisTag.getTagNumber());
                    if(mrte != null) {
                        if(mrte.isObsolete()) {
                            if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_OBS_TAGS)) {
                                result.add(new MarcValidatorResultRow(MarcValidatorResultType.OBSOLETE_TAG, thisTag.getTagNumber()));
                            }
                        } else if(!mrte.isSkipped())  {
                            result.addAll(validateTag(mrte, thisTag, displayFlag));
                        }
                    } else {
                        if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_INV_TAGS)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_TAGNUM, thisTag.getTagNumber()));
                        }
                    }
                }
            }
        }

        return result;
    }

    private static boolean checkLocalTag(int tagNumber) {
        boolean localTag = false;
        // 9XX tags are local
        if(tagNumber >= 900) {
            localTag = true;
        } else if (tagNumber == 490) {
            localTag = false; // 490 is the one and only tag with a 9 that is not local
        } else if (tagNumber % 10 == 9) {
            localTag = true;
        } else if (((tagNumber / 10) % 10) == 9) {
            localTag = true;
        }
        return localTag;
    }

    public static List<MarcValidatorResultRow> validateTag(MarcTagValue tagValue, MarcTag tag, int displayFlag) {
        List<MarcValidatorResultRow> result = new ArrayList<MarcValidatorResultRow>();

        MarcLineEntryValue ruleSet1 = tagValue.getIndByOffset(0).getEntryByCode(tag.getFirstIndicator());
        if(ruleSet1 == null) {
            if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_INV_IND)) {
                result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_INDICATOR_1, tag.getFirstIndicator(), tag.getTagNumber()));
            }
        } else if(ruleSet1.isObsolete()) {
            if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_OBS_IND)) {
                result.add(new MarcValidatorResultRow(MarcValidatorResultType.OBSOLETE_INDICATOR_1, tag.getFirstIndicator(), tag.getTagNumber()));
            }
        }
        MarcLineEntryValue ruleSet2 = tagValue.getIndByOffset(1).getEntryByCode(tag.getSecondIndicator());
        if(ruleSet2 == null) {
            if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_INV_IND)) {
                result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_INDICATOR_2, tag.getSecondIndicator(), tag.getTagNumber()));
            }
        } else if(ruleSet2.isObsolete()) {
            if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_OBS_IND)) {
                result.add(new MarcValidatorResultRow(MarcValidatorResultType.OBSOLETE_INDICATOR_2, tag.getSecondIndicator(), tag.getTagNumber()));
            }
        }

        // Does this tag have required subfields
        if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_REQ_SUBS)) {
            Iterator<MarcLineEntryValue> allSubsIter = tagValue.getSubfields().iterator();
            while(allSubsIter.hasNext()) {
                MarcLineEntryValue mrlee = allSubsIter.next();
                if(mrlee.isRequired()) {
                    if(tag.getSubfields(mrlee.getCode().charAt(0)).isEmpty()) {
                        result.add(new MarcValidatorResultRow(MarcValidatorResultType.SUBFIELD_REQUIRED, mrlee.getCode().charAt(0), tag.getTagNumber()));
                    }
                }
            }
        }

        // Is there a validator for the tag?
        if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_TAG_DATA_VAL)) {
            if(MarcWorxStringHelper.isNotEmpty(tagValue.getValidator())) {
                String valResult = tagValue.loadValidator().getValidator().validateTag(tag);
                if(MarcWorxStringHelper.isNotEmpty(valResult)) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.TAG_DATA_VALIDATION, tag.getTagNumber(), valResult));
                }
            }
        }

        // Validate multitag existence for nonrepeatables.
        if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_NR_SUBS)) {
            Iterator<MarcLineEntryValue> iter = tagValue.getSubfields().iterator();
            while(iter.hasNext()) {
                MarcLineEntryValue mrlee = iter.next();
                if(mrlee.isNonrepeatable() && MarcWorxStringHelper.isNotEmpty(mrlee.getCode())) {
                    if(tag.getSubfields(mrlee.getCode().charAt(0)).size() > 1) {
                        result.add(new MarcValidatorResultRow(MarcValidatorResultType.TOO_MANY_NON_RPT_SUBFIELDS, mrlee.getCode(), tag.getTagNumber()));
                    }
                }
            }
        }
        // Now, check other flags
        List<MarcSubfield> allSubs = tag.getSubfields();
        Iterator<MarcSubfield> iterSubs = allSubs.iterator();
        while(iterSubs.hasNext()) {
            MarcSubfield sub = iterSubs.next();
            MarcLineEntryValue subLine = tagValue.getSubfieldByCode(sub.getCode());
            if(subLine == null) {
                if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_INV_SUBS)) {
                    result.add(new MarcValidatorResultRow(MarcValidatorResultType.INVALID_SUBFIELD, sub.getCode(), tag.getTagNumber()));
                }
            } else {
                if(subLine.isObsolete()) {
                    if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_OBS_SUBS)) {
                        result.add(new MarcValidatorResultRow(MarcValidatorResultType.OBSOLETE_SUBFIELD, sub.getCode(), tag.getTagNumber()));
                    }
                }
                if(MarcLimitersConstants.containsFlag(displayFlag, MarcLimitersConstants.DISPLAY_SUB_DATA_VAL)) {
                    if(MarcWorxStringHelper.isNotEmpty(subLine.getValidator())) {
                        String valResult = subLine.loadValidator().getValidator().validateData(sub.getData());
                        if(MarcWorxStringHelper.isNotEmpty(valResult)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.SUBFIELD_DATA_VALIDATION, sub.getCode(), tag.getTagNumber(), valResult));
                        }
                    }
                    if(MarcWorxStringHelper.isNotEmpty(subLine.getValidationdata())) {
                        String valResult = tagValue.validateDataInSubfield(subLine.getValidationdata(), sub.getData());
                        if(MarcWorxStringHelper.isNotEmpty(valResult)) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.SUBFIELD_DATA_VALIDATION, sub.getCode(), tag.getTagNumber(), valResult));
                        }
                    }
                    if(subLine.getFixedlength() > 0) {
                        if(subLine.getFixedlength() != sub.getData().length()) {
                            result.add(new MarcValidatorResultRow(MarcValidatorResultType.SUBFIELD_DATA_VALIDATION, sub.getCode(), tag.getTagNumber(), "Data in this subfield must contain " + subLine.getFixedlength() + " characters"));
                        }
                    }
                }

            }
        }

        return result;
    }
    
}
