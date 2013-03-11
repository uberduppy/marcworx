package org.talwood.marcworx.locparser.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="codeTables")
public class CodeTableContainer {
    
    List<CodeTableElement> codeTables = new ArrayList<CodeTableElement>();
    
    public CodeTableContainer() {
        
    }

    @XmlElement(name="codeTable")
    public List<CodeTableElement> getCodeTables() {
        return codeTables;
    }

    public void setCodeTables(List<CodeTableElement> codeTables) {
        this.codeTables = codeTables;
    }
    
    
}
