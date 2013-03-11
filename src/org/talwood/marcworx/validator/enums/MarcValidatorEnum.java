/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.talwood.marcworx.validator.enums;

/**
 *
 * @author twalker
 */
public enum MarcValidatorEnum {
    PASS(0, "", new AlwaysPassRuleValidator(), "", MarcValidatorTypeEnum.UNNOWN),
    LCCN(1, "LCCNValidator", new LccnRuleDataValidator(), "LCCN format", MarcValidatorTypeEnum.SUBFIELD),
    ISBN(2, "ISBNValidator", new IsbnRuleDataValidator(), "ISBN format", MarcValidatorTypeEnum.SUBFIELD),
    ISSN(3, "ISSNValidator", new IssnRuleDataValidator(), "ISSN format", MarcValidatorTypeEnum.SUBFIELD),
    IND_0_7_NEEDS_2(4, "IND_0_7_NEEDS_SUB_2", new Ind07NeedsSub2TagValidator(), "If ind 0 is 7,<br>sub 2 is required", MarcValidatorTypeEnum.TAG),
    IND_1_7_NEEDS_2(5, "IND_1_7_NEEDS_SUB_2", new Ind17NeedsSub2TagValidator(), "If ind 1 is 7,<br>sub 2 is required", MarcValidatorTypeEnum.TAG),
    IND_0_SPACE_NEEDS_2(6, "IND_0_SPACE_NEEDS_SUB_2", new Ind0SpaceNeedsSub2TagValidator(), "If ind 0 is space,<br>sub 2 is required", MarcValidatorTypeEnum.TAG),
    YYYYMMDD(7, "YYYYMMDDValidator", new YYYYMMDDRuleDataValidator(), "YYYYMMDD format", MarcValidatorTypeEnum.SUBFIELD),
    CODEN(8, "CODENValidator", new CodenRuleDataValidator(), "CODEN format", MarcValidatorTypeEnum.SUBFIELD),
    SUB_P_NEEDS_2(9, "SUB_P_REQUIRES_SUB_2", new SubPNeedsSub2TagValidator(), "If sub p exists,<br>sub 2 required", MarcValidatorTypeEnum.TAG),
    SUB_2_NEEDS_P(10, "SUB_2_REQUIRES_SUB_B", new Sub2NeedsSubBTagValidator(), "If sub 2 exists,<br>sub b is required", MarcValidatorTypeEnum.TAG),
    DEWEY(11, "DeweyValidator", new DeweyRuleDataValidator(), "Dewey", MarcValidatorTypeEnum.SUBFIELD),
    YYYYMMDASH(12, "YYYYMMDashValidator", new YYYYMMDashRuleDataValidator(), "YYYYMM or <br>YYYY-- format", MarcValidatorTypeEnum.SUBFIELD),
    EMAIL(13, "EmailValidator", new EmailRuleDataValidator(), "Email validation", MarcValidatorTypeEnum.SUBFIELD),
    SIXDIGITS(14, "SixDigitsValidator", new DDDDDDRuleDataValidator(), "6 digits required", MarcValidatorTypeEnum.SUBFIELD),
    SUB_I_NEEDS_1_8(15, "SUB_I_NEEDS_IND_1_8", new SubINeedsInd18TagValidator(), "If sub i exists<br>ind 1 must be 8", MarcValidatorTypeEnum.TAG),
    POSTALCODE(16, "POSTAL_CODE_IN_A_B", new PostalCodeTagValidator(), "Postal Code format", MarcValidatorTypeEnum.SUBFIELD),

    FIXED_CHECK_BOX(17, "CheckBoxValidator", new FixedFieldCheckBoxValidator(), "Fixed Check Box", MarcValidatorTypeEnum.FIXED),
    FIXED_DROP_DOWN(18, "DropDownValidator", new FixedFieldDropDownValidator(), "Fixed Drop Down", MarcValidatorTypeEnum.FIXED),
    FIXED_UNDEFIEND(19, "UndefinedValidator", new AlwaysPassRuleValidator(), "Fixed Undefined", MarcValidatorTypeEnum.FIXED),
    FIXED_NUMBER(20, "NumberValidator", new FixedFieldNumberValidator(), "Fixed Number", MarcValidatorTypeEnum.FIXED),
    FIXED_PASS(21, "PassValidator", new AlwaysPassRuleValidator(), "Fixed Pass", MarcValidatorTypeEnum.FIXED),
    FIXED_CCYYMM(22, "DateCCYYMMValidator", new FixedFieldDateCCYYMMValidator(), "Fixed CCYYMM", MarcValidatorTypeEnum.FIXED),
    FIXED_YYYY(23, "DateYYYYValidator", new FixedFieldYYYYValidator(), "Fixed YYYY", MarcValidatorTypeEnum.FIXED),

    ;

    private int index;
    private String name;
    private BaseMarcRuleValidator validator;
    private String description;
    private MarcValidatorTypeEnum typeEnum;

    private MarcValidatorEnum(int index, String name, BaseMarcRuleValidator validator, String description, MarcValidatorTypeEnum typeEnum) {
        this.index = index;
        this.name = name;
        this.validator = validator;
        this.description = description;
        this.typeEnum = typeEnum;
    }

    public static MarcValidatorEnum findByValidatorName(String searchName) {
        MarcValidatorEnum result = PASS;
        for(int x = 0; x < values().length; x++) {
            if(values()[x].getName().equals(searchName)) {
                result = values()[x];
                break;
            }
        }

        return result;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public BaseMarcRuleValidator getValidator() {
        return validator;
    }

    public String getDescription() {
        return description;
    }

}
