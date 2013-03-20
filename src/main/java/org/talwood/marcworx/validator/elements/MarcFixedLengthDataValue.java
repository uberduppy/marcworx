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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;

@XmlRootElement
public class MarcFixedLengthDataValue extends BaseXmlDataValue {
    
    private List<MarcFixedLengthOffsetValue> offsetElementList = new ArrayList<MarcFixedLengthOffsetValue>();
    private String pertinenttypes;
    private String comment;
    private int datalength;
    
    public MarcFixedLengthDataValue() {
        
    }

    @Override
    public void updateMembers() throws ConstraintException {
        for(MarcFixedLengthOffsetValue val : offsetElementList) {
            val.update();
        }
    }
    
    @XmlElement(name="COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @XmlAttribute(name = "datalength")
    public int getDatalength() {
        return datalength;
    }

    public void setDatalength(int datalength) {
        this.datalength = datalength;
    }

    @XmlElement(name = "OFFSET")
    public List<MarcFixedLengthOffsetValue> getOffsetElementList() {
        return offsetElementList;
    }

    public void setOffsetElementList(List<MarcFixedLengthOffsetValue> offsetElementList) {
        this.offsetElementList = offsetElementList;
    }

    @XmlAttribute(name = "types")
    public String getPertinenttypes() {
        return pertinenttypes;
    }

    public void setPertinenttypes(String pertinenttypes) {
        this.pertinenttypes = pertinenttypes;
    }
    
    public void addOffset(MarcFixedLengthOffsetValue offsetEl) {
        offsetElementList.add(offsetEl);
    }
    
    
}
