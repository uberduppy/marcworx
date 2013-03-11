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
