package org.talwood.marcworx.locparser;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodeTableElement {
    
    private String name;
    private String date;
    private int number;
    private String note;
    private List<CharacterSetElement> charSets = new ArrayList<CharacterSetElement>();
    
    public CodeTableElement() {
        
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
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @XmlElement(name="note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @XmlElement(name="characterSet")
    public List<CharacterSetElement> getCharSets() {
        return charSets;
    }

    public void setCharSets(List<CharacterSetElement> charSets) {
        this.charSets = charSets;
    }
    
    
}
