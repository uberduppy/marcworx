package org.talwood.marcworx.locparser.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupingElement {

    private String name;
    private String date;
    private String number;
    private String note;
    private List<CodeElement> codeList = new ArrayList<CodeElement>();

    public GroupingElement() {
        
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlAttribute(name="number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlElement(name="code")
    public List<CodeElement> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CodeElement> codeList) {
        this.codeList = codeList;
    }

    @XmlElement(name="note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
