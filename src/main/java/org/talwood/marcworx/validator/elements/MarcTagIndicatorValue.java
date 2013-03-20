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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.annotation.PostProcess;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;

@XmlRootElement
public class MarcTagIndicatorValue extends BaseXmlDataValue {
    private int position;
    private String name;
    private List<MarcLineEntryValue> entryList = new ArrayList<MarcLineEntryValue>();
    private Map<String, MarcLineEntryValue> entryMap = new HashMap<String, MarcLineEntryValue>();
    public static final String IND_VALUES = " 0123456789";
    
    public MarcTagIndicatorValue() {
        
    }
    
    public static MarcTagIndicatorValue generateValue(int position, String name, boolean localTag) {
        MarcTagIndicatorValue ind = new MarcTagIndicatorValue();
        ind.setName(name);
        ind.setPosition(position);
        for(int x = 0; x < IND_VALUES.length(); x++) {
            MarcLineEntryValue entry = new MarcLineEntryValue();
            entry.setCode(String.valueOf(IND_VALUES.charAt(x)));
            entry.setDefval(false);
            entry.setShorttext(localTag ? "Local" : "Invalid");
            entry.setText(localTag ? "Local" : "Invalid");
            ind.addIndicatorCodeElement(entry);
        }
        return ind;
    }
    
    @XmlAttribute
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @XmlElement(name = "ENTRY")
    public List<MarcLineEntryValue> getEntryList() {
        return entryList;
    }

    public MarcLineEntryValue getEntryByCode(String code) {
        return entryMap.get(code);
    }

    public MarcLineEntryValue getEntryByCode(char code) {
        return entryMap.get(String.valueOf(code));
    }

    @XmlElement(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostProcess
    public void postProcess() throws ConstraintException {
        entryMap.clear();
        for(MarcLineEntryValue value : entryList) {
            entryMap.put(value.getCode(), value);
        }
    }

    @Override
    public void updateMembers() throws ConstraintException {
        for(MarcLineEntryValue value : entryList) {
            value.update();
        }
    }
    
    // NOTE: NEEDED FOR EXTRA
    public void addIndicatorCodeElement(MarcLineEntryValue element) {
        entryList.add(element);
        entryMap.put(element.getCode(), element);
    }
    
    
}
