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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.annotation.PostProcess;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.validator.enums.MarcValidatorEnum;

@XmlRootElement
public class MarcTagValue extends BaseXmlDataValue {
    
    private int tagNumber;
    private boolean nonrepeatable;
    private boolean obsolete;
    private boolean skipped;
    private String validator;
    private String name;
    private String shortName;
    private int obsoletewhen = 0;
    private boolean local;

    private List<MarcTagIndicatorValue> indList = new ArrayList<MarcTagIndicatorValue>();
    private Map<Integer, MarcTagIndicatorValue> indMap = new HashMap<Integer, MarcTagIndicatorValue>();

    private List<MarcLineEntryValue> subfieldList = new ArrayList<MarcLineEntryValue>();
    private Map<String, MarcLineEntryValue> subfieldMap = new HashMap<String, MarcLineEntryValue>();
    
    public MarcTagValue() {
        
    }
    
    public static boolean determineLocalTag(int tagnumber) {
        return (tagnumber % 10 == 9 || tagnumber / 100 == 9 || (tagnumber / 10) % 10 == 9);
    }
    
    public static MarcTagValue createTagValue(int tagnumber) {
        MarcTagValue value = new MarcTagValue();
        boolean localTag = determineLocalTag(tagnumber);
        value.setTagNumber(tagnumber);
        value.setLocal(localTag);
        
        MarcTagIndicatorValue ind0 = MarcTagIndicatorValue.generateValue(0, "Undefined", localTag);
        MarcTagIndicatorValue ind1 = MarcTagIndicatorValue.generateValue(1, "Undefined", localTag);
        value.addIndicatorElement(ind0);
        value.addIndicatorElement(ind1);
        
        final String subVals = "abcdefghijklmnopqrstuvwxyz0123456789";
        for(int x = 0; x < subVals.length(); x++) {
            MarcLineEntryValue sub = new MarcLineEntryValue();
            sub.setCode(String.valueOf(subVals.charAt(x)));
            sub.setDefval(false);
            sub.setShorttext(localTag ? "Local" : "Invalid");
            sub.setText(localTag ? "Local" : "Invalid");
            
            value.addSubfieldElement(sub);
        }
        
        return value;
    }

    @XmlAttribute(name = "tagnumber")
    public int getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    @XmlElement(name = "IND")
    public List<MarcTagIndicatorValue> getIndList() {
        return indList;
    }

    public void setIndList(List<MarcTagIndicatorValue> indList) {
        this.indList = indList;
    }

    public Map<Integer, MarcTagIndicatorValue> getIndMap() {
        return indMap;
    }

    public void setIndMap(Map<Integer, MarcTagIndicatorValue> indMap) {
        this.indMap = indMap;
    }

    @XmlAttribute
    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    @XmlElement(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public boolean isNonrepeatable() {
        return nonrepeatable;
    }

    public void setNonrepeatable(boolean nonrepeatable) {
        this.nonrepeatable = nonrepeatable;
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

    @XmlElement(name = "SHORTNAME")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @XmlAttribute
    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    @XmlElementWrapper(name = "SUB")
    @XmlElement(name = "ENTRY")
    public List<MarcLineEntryValue> getSubfields() {
        return subfieldList;
    }

    public void setSubfields(List<MarcLineEntryValue> subfieldList) {
        this.subfieldList = subfieldList;
    }

    @XmlAttribute
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }
    
    public MarcTagIndicatorValue getIndByOffset(int offset) {
        return indMap.get(new Integer(offset));
    }
    
    public MarcLineEntryValue getSubfieldByCode(String code) {
        return subfieldMap.get(code);
    }
    
    public MarcLineEntryValue getSubfieldByCode(char code) {
        return subfieldMap.get(String.valueOf(code));
    }
    

    @PostProcess
    public void postProcess() throws ConstraintException {
        indMap.clear();
        for(MarcTagIndicatorValue val : indList) {
            indMap.put(new Integer(val.getPosition()), val);
        }
        subfieldMap.clear();
        for(MarcLineEntryValue sub : subfieldList) {
            subfieldMap.put(sub.getCode(), sub);
        }

        if(indMap.get(Integer.valueOf(0)) == null) {
            MarcTagIndicatorValue v = MarcTagIndicatorValue.generateValue(0, "Undefined", determineLocalTag(tagNumber));
            indList.add(v);
            indMap.put(Integer.valueOf(0), v);
        }
        if(indMap.get(Integer.valueOf(1)) == null) {
            MarcTagIndicatorValue v = MarcTagIndicatorValue.generateValue(1, "Undefined", determineLocalTag(tagNumber));
            indList.add(v);
            indMap.put(Integer.valueOf(1), v);
        }
    }
    
    @Override
    public void updateMembers() throws ConstraintException {
        for(MarcTagIndicatorValue val : indList) {
            val.update();
        }
        for(MarcLineEntryValue sub : subfieldList) {
            sub.update();
        }
    }
    
    // MAY NOT BE NEEDED
    public MarcValidatorEnum loadValidator() {
        MarcValidatorEnum result = MarcValidatorEnum.PASS;
        if(MarcWorxStringHelper.isNotEmpty(getValidator())) {
            result = MarcValidatorEnum.findByValidatorName(getValidator());
        }
        return result;
    }
    
    public String validateDataInSubfield(String pattern, String data) {
        String result = null;
        boolean found = false;
        String[] masks = pattern.split(",");
        for(int x = 0; x < masks.length; x++) {
            if(MarcWorxObjectHelper.equals(data, masks[x])) {
                found = true;
                break;
            }
        }
        if(!found) {
            StringBuilder sb = new StringBuilder();
            sb.append("Only");
            for(int x = 0; x < masks.length; x++) {
                if(x + 1 == masks.length) {
                    sb.append(" or");
                } else if (x > 0) {
                    sb.append(",");
                }
                sb.append(" \"").append(masks[x]).append("\"");
            }
            if(masks.length == 1) {
                sb.append(" is");
            } else {
                sb.append(" are");
            }
            sb.append(" allowed");
            result = sb.toString();
        }
        return result;
    }

    public void addSubfieldElement(MarcLineEntryValue element) {
        subfieldList.add(element);
        subfieldMap.put(element.getCode(), element);
    }
    
    public void addIndicatorElement(MarcTagIndicatorValue element) {
        indList.add(element);
        indMap.put(new Integer(element.getPosition()), element);
    }
    
    
    
}
