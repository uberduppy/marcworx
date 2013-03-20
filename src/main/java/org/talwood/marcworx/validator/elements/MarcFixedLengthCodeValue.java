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

@XmlRootElement
public class MarcFixedLengthCodeValue extends BaseXmlDataValue {
    
    private String code = "";
    private String text = "";
    private String shorttext = "";
    private boolean obsolete = false;
    private int obsoletewhen = 0;
    
    public MarcFixedLengthCodeValue() {
        
    }

    @Override
    public void updateMembers() throws ConstraintException {
        // No data members need to be updated
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

    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String gimmeDisplayText() {
        return (MarcWorxStringHelper.isEmpty(getShorttext()) ? getText() : getShorttext());
    }
    
    
}
