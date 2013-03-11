package org.talwood.marcworx.locparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodeElement {
    private boolean combining;
    private String marc;
    private String ucs;
    private String utf8;
    
    public CodeElement() {
        
    }

    @XmlElement(name="marc")
    public String getMarc() {
        return marc;
    }

    public void setMarc(String marc) {
        this.marc = marc;
    }

    @XmlElement(name="ucs")
    public String getUcs() {
        return ucs;
    }

    public void setUcs(String ucs) {
        this.ucs = ucs;
    }

    @XmlElement(name="utf-8")
    public String getUtf8() {
        return utf8;
    }

    public void setUtf8(String utf8) {
        this.utf8 = utf8;
    }

    @XmlElement(name="isCombining")
    public boolean isCombining() {
        return combining;
    }

    public void setCombining(boolean combining) {
        this.combining = combining;
    }
    
    
}
