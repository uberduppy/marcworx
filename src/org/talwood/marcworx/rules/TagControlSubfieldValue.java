package org.talwood.marcworx.rules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagControlSubfieldValue {

    private String desc;
    private int offset;
    private List<TagControlSubfieldCodeValue> controlCodeList = new ArrayList<TagControlSubfieldCodeValue>();
    
    
    public TagControlSubfieldValue() {
        
    }

    @XmlAttribute
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @XmlElement(name = "code")
    public List<TagControlSubfieldCodeValue> getControlCodeList() {
        return controlCodeList;
    }

    public void setControlCodeList(List<TagControlSubfieldCodeValue> controlCodeList) {
        this.controlCodeList = controlCodeList;
    }

    
    
}
