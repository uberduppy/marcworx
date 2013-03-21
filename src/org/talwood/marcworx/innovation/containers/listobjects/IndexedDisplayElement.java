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

package org.talwood.marcworx.innovation.containers.listobjects;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="DATA")
public class IndexedDisplayElement implements Serializable {

    private String displayable;
    private String indexed;
    
    public IndexedDisplayElement() {
        
    }
    
    public IndexedDisplayElement(String displayable, String indexed) {
        this.displayable = displayable;
        this.indexed = indexed;
    }
    

    @XmlElement(name="displayable")
    public String getDisplayable() {
        return displayable;
    }

    public void setDisplayable(String displayable) {
        this.displayable = displayable;
    }

    @XmlElement(name="indexed")
    public String getIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }
    
}
