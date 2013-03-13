/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.validator.constants;

public class DisplayFlagSettingsElement {

    // Validator flags
    private boolean validateNonRepeatableTags = true;
    private boolean validateObsoleteTags = true;
    private boolean validateInvalidTags = true;
    private boolean validateObsoleteIndicators = true;
    private boolean validateInvalidIndicators = true;
    private boolean validateNonRepeatableSubfields = true;
    private boolean validateObsoleteSubfields = true;
    private boolean validateInvalidSubfields = true;
    private boolean validateRequiredSubfields = true;
    private boolean validateTagData = true;
    private boolean validateSubfieldData = true;
    private boolean validateLocalData = true;

    public DisplayFlagSettingsElement() {

    }

    public int generateValidatorFlag() {
        int result = MarcLimitersConstants.DISPLAY_NONE;
        result += validateNonRepeatableTags ? MarcLimitersConstants.DISPLAY_NR_TAGS : 0;
        result += validateObsoleteTags ? MarcLimitersConstants.DISPLAY_OBS_TAGS : 0;
        result += validateInvalidTags ? MarcLimitersConstants.DISPLAY_INV_TAGS : 0;
        result += validateObsoleteIndicators ? MarcLimitersConstants.DISPLAY_OBS_IND : 0;
        result += validateInvalidIndicators ? MarcLimitersConstants.DISPLAY_INV_IND : 0;
        result += validateNonRepeatableSubfields ? MarcLimitersConstants.DISPLAY_NR_SUBS : 0;
        result += validateObsoleteSubfields ? MarcLimitersConstants.DISPLAY_OBS_SUBS : 0;
        result += validateInvalidSubfields ? MarcLimitersConstants.DISPLAY_INV_SUBS : 0;
        result += validateRequiredSubfields ? MarcLimitersConstants.DISPLAY_REQ_SUBS : 0;
        result += validateTagData ? MarcLimitersConstants.DISPLAY_TAG_DATA_VAL : 0;
        result += validateSubfieldData ? MarcLimitersConstants.DISPLAY_SUB_DATA_VAL : 0;
        return result;
    }


    public boolean isValidateInvalidIndicators() {
        return validateInvalidIndicators;
    }

    public void setValidateInvalidIndicators(boolean validateInvalidIndicators) {
        this.validateInvalidIndicators = validateInvalidIndicators;
    }

    public boolean isValidateInvalidSubfields() {
        return validateInvalidSubfields;
    }

    public void setValidateInvalidSubfields(boolean validateInvalidSubfields) {
        this.validateInvalidSubfields = validateInvalidSubfields;
    }

    public boolean isValidateInvalidTags() {
        return validateInvalidTags;
    }

    public void setValidateInvalidTags(boolean validateInvalidTags) {
        this.validateInvalidTags = validateInvalidTags;
    }

    public boolean isValidateNonRepeatableSubfields() {
        return validateNonRepeatableSubfields;
    }

    public void setValidateNonRepeatableSubfields(boolean validateNonRepeatableSubfields) {
        this.validateNonRepeatableSubfields = validateNonRepeatableSubfields;
    }

    public boolean isValidateNonRepeatableTags() {
        return validateNonRepeatableTags;
    }

    public void setValidateNonRepeatableTags(boolean validateNonRepeatableTags) {
        this.validateNonRepeatableTags = validateNonRepeatableTags;
    }

    public boolean isValidateObsoleteIndicators() {
        return validateObsoleteIndicators;
    }

    public void setValidateObsoleteIndicators(boolean validateObsoleteIndicators) {
        this.validateObsoleteIndicators = validateObsoleteIndicators;
    }

    public boolean isValidateObsoleteSubfields() {
        return validateObsoleteSubfields;
    }

    public void setValidateObsoleteSubfields(boolean validateObsoleteSubfields) {
        this.validateObsoleteSubfields = validateObsoleteSubfields;
    }

    public boolean isValidateObsoleteTags() {
        return validateObsoleteTags;
    }

    public void setValidateObsoleteTags(boolean validateObsoleteTags) {
        this.validateObsoleteTags = validateObsoleteTags;
    }

    public boolean isValidateRequiredSubfields() {
        return validateRequiredSubfields;
    }

    public void setValidateRequiredSubfields(boolean validateRequiredSubfields) {
        this.validateRequiredSubfields = validateRequiredSubfields;
    }

    public boolean isValidateSubfieldData() {
        return validateSubfieldData;
    }

    public void setValidateSubfieldData(boolean validateSubfieldData) {
        this.validateSubfieldData = validateSubfieldData;
    }

    public boolean isValidateTagData() {
        return validateTagData;
    }

    public void setValidateTagData(boolean validateTagData) {
        this.validateTagData = validateTagData;
    }

    public boolean isValidateLocalData() {
        return validateLocalData;
    }

    public void setValidateLocalData(boolean validateLocalData) {
        this.validateLocalData = validateLocalData;
    }


}
