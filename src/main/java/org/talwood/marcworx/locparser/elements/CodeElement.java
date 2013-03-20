/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.locparser.elements;

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
