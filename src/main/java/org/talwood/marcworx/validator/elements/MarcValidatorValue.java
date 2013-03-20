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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.annotation.PostProcess;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.helpers.TupleContainer;
import org.talwood.marcworx.marc.constants.MarcTagConstants;

@XmlRootElement
public class MarcValidatorValue extends BaseXmlDataValue {
    private String code;

    private Map<Integer, MarcControlTagValue> controlTagMap = new HashMap<Integer, MarcControlTagValue>();
    private List<MarcControlTagValue> controlTagList = new ArrayList<MarcControlTagValue>();
    
    private Map<Integer, MarcTagValue> tagMap = new HashMap<Integer, MarcTagValue>();
    private List<MarcTagValue> tagList = new ArrayList<MarcTagValue>();
    private List<MarcTagValue> nonRepeatTagList = new ArrayList<MarcTagValue>();

    private List<MarcFixedLengthDataValue> leaderList = new ArrayList<MarcFixedLengthDataValue>();
    private List<MarcFixedLengthDataValue> fixedLengthList = new ArrayList<MarcFixedLengthDataValue>();
    private List<MarcFixedLengthDataValue> fixedLength008List = new ArrayList<MarcFixedLengthDataValue>();
    private List<MarcFixedLengthDataValue> physDescList = new ArrayList<MarcFixedLengthDataValue>();

    private Map<TupleContainer<Long, String>, MarcFixedLengthDataValue> fixedDataMap = new HashMap<TupleContainer<Long, String>, MarcFixedLengthDataValue>();

    public final String VALID_INDS = " 0123456789";
    public final String VALID_SUBS = "abcdefghijklmnopqrstuvwxyz0123456789";

    public MarcValidatorValue() {
        
    }

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "CONTROL")
    public List<MarcControlTagValue> getControlTagList() {
        return controlTagList;
    }

    public void setControlTagList(List<MarcControlTagValue> controlTagList) {
        this.controlTagList = controlTagList;
    }
    
    
    
    @XmlElement(name = "FIXED008")
    public List<MarcFixedLengthDataValue> getFixedLength008List() {
        return fixedLength008List;
    }

    public void setFixedLength008List(List<MarcFixedLengthDataValue> fixedLength008List) {
        this.fixedLength008List = fixedLength008List;
    }

    @XmlElement(name = "FIXED")
    public List<MarcFixedLengthDataValue> getFixedLengthList() {
        return fixedLengthList;
    }

    public void setFixedLengthList(List<MarcFixedLengthDataValue> fixedLengthList) {
        this.fixedLengthList = fixedLengthList;
    }

    @XmlElement(name = "LDR")
    public List<MarcFixedLengthDataValue> getLeaderList() {
        return leaderList;
    }

    public void setLeaderList(List<MarcFixedLengthDataValue> leaderList) {
        this.leaderList = leaderList;
    }

    @XmlElement(name = "PHYSDESC")
    public List<MarcFixedLengthDataValue> getPhysDescList() {
        return physDescList;
    }

    public void setPhysDescList(List<MarcFixedLengthDataValue> physDescList) {
        this.physDescList = physDescList;
    }

    public List<MarcTagValue> getNonRepeatTagList() {
        return nonRepeatTagList;
    }

    public void setNonRepeatTagList(List<MarcTagValue> nonRepeatTagList) {
        this.nonRepeatTagList = nonRepeatTagList;
    }

    @XmlElement(name = "TAG")
    public List<MarcTagValue> getTagList() {
        return tagList;
    }

    public void setTagList(List<MarcTagValue> tagList) {
        this.tagList = tagList;
    }

    public MarcTagValue findTagElement(int tagNumber) {
        return tagMap.get(new Integer(tagNumber));
    }
    
    @PostProcess
    public void postProcess() throws ConstraintException {
        nonRepeatTagList.clear();
        tagMap.clear();
        fixedDataMap.clear();
        for(MarcTagValue tag : tagList) {
            if(tag.isNonrepeatable()) {
                nonRepeatTagList.add(tag);
            }
            tagMap.put(new Integer(tag.getTagNumber()), tag);
        }
        for(int x = 10; x < 1000; x++) {
            MarcTagValue t = tagMap.get(new Integer(x));
            if(t == null) {
                t = MarcTagValue.createTagValue(x);
                tagList.add(t);
                tagMap.put(new Integer(x), t);
            }
        }
        // Each of the four fixed data elements
        for(MarcFixedLengthDataValue dta8 : fixedLength008List) {
            String types = dta8.getPertinenttypes();
            if(MarcWorxStringHelper.isNotEmpty(types)) {
                for(int x = 0; x < types.length(); x++) {
                    fixedDataMap.put(new TupleContainer<Long, String>(new Long(8), String.valueOf(types.charAt(x))), dta8);
                }
            }
        }
        
        for(MarcFixedLengthDataValue phys : physDescList) {
            String types = phys.getPertinenttypes();
            if(MarcWorxStringHelper.isNotEmpty(types)) {
                for(int x = 0; x < types.length(); x++) {
                    fixedDataMap.put(new TupleContainer<Long, String>(new Long(7), String.valueOf(types.charAt(x))), phys);
                }
            }
        }
        
        for(MarcFixedLengthDataValue dta : fixedLengthList) {
            String types = dta.getPertinenttypes();
            if(MarcWorxStringHelper.isNotEmpty(types)) {
                for(int x = 0; x < types.length(); x++) {
                    fixedDataMap.put(new TupleContainer<Long, String>(new Long(6), String.valueOf(types.charAt(x))), dta);
                }
            }
        }
        
        for(MarcFixedLengthDataValue ldr : leaderList) {
            String types = ldr.getPertinenttypes();
            if(MarcWorxStringHelper.isNotEmpty(types)) {
                for(int x = 0; x < types.length(); x++) {
                    fixedDataMap.put(new TupleContainer<Long, String>(new Long(MarcTagConstants.LEADER), String.valueOf(types.charAt(x))), ldr);
                }
            }
        }
        
    }

    @Override
    public void updateMembers() throws ConstraintException {
        for(MarcTagValue tag : tagList) {
            tag.update();
        }

        
    }

    public MarcFixedLengthOffsetValue findLeaderElementByOffset(int offset) {
        Iterator<MarcFixedLengthDataValue> iter = leaderList.iterator();
        if(iter.hasNext()) {
            MarcFixedLengthDataValue de = iter.next();
            Iterator<MarcFixedLengthOffsetValue> oe = de.getOffsetElementList().iterator();
            while(oe.hasNext()) {
                MarcFixedLengthOffsetValue loe = oe.next();
                if(loe.getPosition() == offset) {
                    return loe;
                }
            }
        }
        return null;
    }

    public  MarcFixedLengthDataValue findDataElementByTagAndCode(int tagNumber, char flag) {
        return fixedDataMap.get(new TupleContainer<Long, String>(new Long(tagNumber), String.valueOf(flag)));
    }

    public MarcControlTagValue findControlTagElement(int tagNumber) {
        return controlTagMap.get(new Integer(tagNumber));
    }
    
}