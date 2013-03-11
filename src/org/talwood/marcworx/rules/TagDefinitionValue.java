package org.talwood.marcworx.rules;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagDefinitionValue {
    private int tagnumber;
    private int type;
    private boolean numeric;
    private String indexed;
    private String repeatable;

    public TagDefinitionValue() {
        
    }

    @XmlAttribute
    public String getIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }

    @XmlAttribute
    public boolean isNumeric() {
        return numeric;
    }

    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }

    @XmlAttribute
    public String getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(String repeatable) {
        this.repeatable = repeatable;
    }

    @XmlAttribute
    public int getTagnumber() {
        return tagnumber;
    }

    public void setTagnumber(int tagnumber) {
        this.tagnumber = tagnumber;
    }

    @XmlAttribute
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
}
