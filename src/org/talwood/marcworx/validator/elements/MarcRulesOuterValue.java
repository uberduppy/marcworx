package org.talwood.marcworx.validator.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;

@XmlRootElement(name = "MARC_RULES")
public class MarcRulesOuterValue extends BaseXmlDataValue {
    private List<MarcValidatorValue> marcValidatorList = new ArrayList<MarcValidatorValue>();

    public MarcRulesOuterValue() {
        
    }

    @XmlElement(name = "MARC_TYPE")
    public List<MarcValidatorValue> getMarcValidatorList() {
        return marcValidatorList;
    }

    public void setMarcValidatorList(List<MarcValidatorValue> marcValidatorList) {
        this.marcValidatorList = marcValidatorList;
    }

    @Override
    public void updateMembers() throws ConstraintException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
