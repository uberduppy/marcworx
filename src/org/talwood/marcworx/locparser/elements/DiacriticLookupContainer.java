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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.talwood.marcworx.locparser.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="Diacritics")
public class DiacriticLookupContainer {

    private List<DiacriticLookupElement> elements = new ArrayList<DiacriticLookupElement>();
    
    public DiacriticLookupContainer() {
        
    }

    @XmlElement(name="DiacriticSet")
    public List<DiacriticLookupElement> getElements() {
        return elements;
    }

    public void setElements(List<DiacriticLookupElement> elements) {
        this.elements = elements;
    }
    
    
}