package org.talwood.marcworx.rules;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rules")
public class MainDefinitionValue {
    List<TagHeadingTypeValue> headingTypeList = new ArrayList<TagHeadingTypeValue>();
    List<TagDefinitionValue> definitionList = new ArrayList<TagDefinitionValue>();
    List<TagControlSubfieldValue> controlSubfieldList = new ArrayList<TagControlSubfieldValue>();

    public MainDefinitionValue() {
        
    }

    @XmlElementWrapper(name = "tagdefs")
    @XmlElement(name = "tag")
    public List<TagDefinitionValue> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(List<TagDefinitionValue> definitionList) {
        this.definitionList = definitionList;
    }

    @XmlElementWrapper(name = "headingtypes")
    @XmlElement(name = "type")
    public List<TagHeadingTypeValue> getHeadingTypeList() {
        return headingTypeList;
    }

    public void setHeadingTypeList(List<TagHeadingTypeValue> headingTypeList) {
        this.headingTypeList = headingTypeList;
    }

    @XmlElementWrapper(name = "controlSubfield")
    @XmlElement(name = "position")
    public List<TagControlSubfieldValue> getControlSubfieldList() {
        return controlSubfieldList;
    }

    public void setControlSubfieldList(List<TagControlSubfieldValue> controlSubfieldList) {
        this.controlSubfieldList = controlSubfieldList;
    }
    
    
}
