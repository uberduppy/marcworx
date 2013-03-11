package org.talwood.marcworx.validator.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

@XmlRootElement
public class MarcFixedLengthCodeValue extends BaseXmlDataValue {
    
    private String code = "";
    private String text = "";
    private String shorttext = "";
    private boolean obsolete = false;
    private int obsoletewhen = 0;
    
    public MarcFixedLengthCodeValue() {
        
    }

    @Override
    public void updateMembers() throws ConstraintException {
        // No data members need to be updated
    }

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @XmlAttribute
    public String getShorttext() {
        return shorttext;
    }

    public void setShorttext(String shorttext) {
        this.shorttext = shorttext;
    }

    @XmlAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String gimmeDisplayText() {
        return (MarcWorxStringHelper.isEmpty(getShorttext()) ? getText() : getShorttext());
    }
    
    
}
