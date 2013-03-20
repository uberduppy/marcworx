package org.talwood.marcworx.validator.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.constraint.BaseXmlDataValue;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;

@XmlRootElement
public class MarcFixedLengthOffsetValue extends BaseXmlDataValue {

    private List<MarcFixedLengthCodeValue> codeElementList = new ArrayList<MarcFixedLengthCodeValue>();
    private int position;
    private int length;
    private String defaultvalue;
    private String desc;
    private String display;
    private String validator;
    
    public MarcFixedLengthOffsetValue() {
    }
    
    

    @Override
    public void updateMembers() throws ConstraintException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @XmlElement(name="CODE")
    public List<MarcFixedLengthCodeValue> getCodeElementList() {
        return codeElementList;
    }

    public void setCodeElementList(List<MarcFixedLengthCodeValue> codeElementList) {
        this.codeElementList = codeElementList;
    }

    @XmlAttribute
    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    @XmlElement(name="DESC")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @XmlAttribute
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @XmlAttribute
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @XmlAttribute
    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }
    
    // THESE MAY NOT BE NEEDED
    public MarcFixedLengthOffsetValue makeCloneFor008(int positionModifier) {
        MarcFixedLengthOffsetValue result = new MarcFixedLengthOffsetValue();
        result.setDefaultvalue(getDefaultvalue());
        result.setDesc(getDesc());
        result.setDisplay(getDisplay());
        result.setLength(getLength());
        result.setPosition(getPosition() + positionModifier);
        result.setValidator(getValidator());
        Iterator<MarcFixedLengthCodeValue> iter = codeElementList.iterator();
        while(iter.hasNext()) {
            result.addCode(iter.next());
        }
        return result;
    }
    
    public void addCode(MarcFixedLengthCodeValue codeEl) {
        codeElementList.add(codeEl);
    }
    
    public boolean containsCode(String checkCode) {
        boolean found = false;
        Iterator<MarcFixedLengthCodeValue> iter = codeElementList.iterator();
        while(iter.hasNext() && !found) {
            found = MarcWorxObjectHelper.equals(checkCode, iter.next().getCode());
        }
        return found;
    }

    public MarcFixedLengthCodeValue findByCode(String code) {
        MarcFixedLengthCodeValue result = null;
        Iterator<MarcFixedLengthCodeValue> iter = codeElementList.iterator();
        while(iter.hasNext() && code != null) {
            MarcFixedLengthCodeValue e = iter.next();
            if(code.equalsIgnoreCase(e.getCode())) {
                result = e;
                break;
            }
        }
        return result;
    }
    
}
