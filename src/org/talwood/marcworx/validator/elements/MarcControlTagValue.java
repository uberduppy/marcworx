package org.talwood.marcworx.validator.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

@XmlRootElement
public class MarcControlTagValue extends BaseXmlDataValue {
    
    private int tagNumber;
    private boolean nonrepeatable;
    private boolean obsolete;
    private String name;
    private String shortName;
    private int obsoletewhen = 0;
    private String validator;
    
    public MarcControlTagValue() {
        
    }

    @Override
    public void updateMembers() throws ConstraintException {
        // No members need to be updated here.
    }

    @XmlElement(name="NAME")
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

    @XmlElement(name="SHORTNAME")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @XmlAttribute(name="tagnumber")
    public int getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    @XmlAttribute
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }
    

    // POSSIBLY EXTRANEOUS METHODS
    public MarcControlTagValue(int tagNumber, String name, String shortName) {
        if(tagNumber < 0 || tagNumber > 10) {
            throw new UnsupportedOperationException("Tag number must be between 001 and 009");
        }
        this.tagNumber = tagNumber;
        this.name = name;
        this.shortName = MarcWorxStringHelper.isNotEmpty(shortName) ? shortName : name;
    }

    public MarcControlTagValue andMakeObsolete() {
        this.obsolete = true;
        return this;
    }

    public MarcControlTagValue andMakeNonRepeatable() {
        this.nonrepeatable = true;
        return this;
    }


    public MarcControlTagValue andSetValidator(String validator) {
        this.validator = validator;
        return this;
    }

    public MarcControlTagValue andSetObsoleteWhen(int obsoleteWhen) {
        this.obsolete = (obsoleteWhen > 0);
        this.obsoletewhen = obsoleteWhen;
        return this;
    }
    
    
}
