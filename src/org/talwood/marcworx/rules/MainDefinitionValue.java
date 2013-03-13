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
package org.talwood.marcworx.rules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rules")
public class MainDefinitionValue {
    List<TagHeadingTypeValue> headingTypeList = new ArrayList<TagHeadingTypeValue>();
    List<TagDefinitionValue> definitionList = new ArrayList<TagDefinitionValue>();
    List<TagControlSubfieldValue> controlSubfieldList = new ArrayList<TagControlSubfieldValue>();

    public MainDefinitionValue() {
        
    }

    @XmlElementWrapper(name = "tagdefs")
    @XmlElement(name = "tag")
    public List<TagDefinitionValue> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(List<TagDefinitionValue> definitionList) {
        this.definitionList = definitionList;
    }

    @XmlElementWrapper(name = "headingtypes")
    @XmlElement(name = "type")
    public List<TagHeadingTypeValue> getHeadingTypeList() {
        return headingTypeList;
    }

    public void setHeadingTypeList(List<TagHeadingTypeValue> headingTypeList) {
        this.headingTypeList = headingTypeList;
    }

    @XmlElementWrapper(name = "controlSubfield")
    @XmlElement(name = "position")
    public List<TagControlSubfieldValue> getControlSubfieldList() {
        return controlSubfieldList;
    }

    public void setControlSubfieldList(List<TagControlSubfieldValue> controlSubfieldList) {
        this.controlSubfieldList = controlSubfieldList;
    }
    
    
}
