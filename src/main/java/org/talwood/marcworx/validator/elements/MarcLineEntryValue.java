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
package org.talwood.marcworx.validator.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.validator.enums.MarcValidatorEnum;

@XmlRootElement
public class MarcLineEntryValue extends BaseXmlDataValue {
    private String code;
    private String replacement;
    private String text;
    private String shorttext;
    private String validator;
    private String validationdata;
    private boolean obsolete;
    private boolean nonrepeatable;
    private boolean required;
    private boolean defval;
    private int fixedlength = 0;
    private int obsoletewhen = 0;
    
    public MarcLineEntryValue() {
        
    }
    
    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    @XmlAttribute
    public boolean isNonrepeatable() {
        return nonrepeatable;
    }

    public void setNonrepeatable(boolean nonrepeatable) {
        this.nonrepeatable = nonrepeatable;
    }

    @XmlAttribute
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    @XmlAttribute
    public String getValidationdata() {
        return validationdata;
    }

    public void setValidationdata(String validationdata) {
        this.validationdata = validationdata;
    }

    @XmlAttribute
    public int getFixedlength() {
        return fixedlength;
    }

    public void setFixedlength(int fixedlength) {
        this.fixedlength = fixedlength;
    }

    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlAttribute
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @XmlAttribute
    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @XmlAttribute
    public boolean isDefval() {
        return defval;
    }

    public void setDefval(boolean defval) {
        this.defval = defval;
    }

    @XmlAttribute
    public int getObsoletewhen() {
        return obsoletewhen;
    }

    public void setObsoletewhen(int obsoletewhen) {
        this.obsoletewhen = obsoletewhen;
    }

    @XmlAttribute
    public String getShorttext() {
        return shorttext;
    }

    public void setShorttext(String shorttext) {
        this.shorttext = shorttext;
    }

    public String gimmeDisplayText() {
        return (MarcWorxStringHelper.isEmpty(getShorttext()) ? getText() : getShorttext());
    }
    

    @Override
    public void updateMembers() throws ConstraintException {
        // No members need updating!
    }
    
    

    public MarcValidatorEnum loadValidator() {
        MarcValidatorEnum result = MarcValidatorEnum.PASS;
        if(!MarcWorxStringHelper.isEmpty(getValidator())) {
            result = MarcValidatorEnum.findByValidatorName(getValidator());
        }
        return result;
    }
    
}
