package org.talwood.marcworx.locparser;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CharacterSetElement {
    
    private String name;
    private String isoCode;
    private List<CodeElement> codeList = new ArrayList<CodeElement>();
    private List<GroupingElement> groupingList = new ArrayList<GroupingElement>();
    
    public CharacterSetElement() {
        
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="ISOcode")
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @XmlElement(name="code")
    public List<CodeElement> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CodeElement> codeList) {
        this.codeList = codeList;
    }

    @XmlElement(name="grouping")
    public List<GroupingElement> getGroupingList() {
        return groupingList;
    }

    public void setGroupingList(List<GroupingElement> groupingList) {
        this.groupingList = groupingList;
    }
    
    
}
