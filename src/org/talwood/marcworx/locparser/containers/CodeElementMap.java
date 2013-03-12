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
package org.talwood.marcworx.locparser.containers;

import java.util.HashMap;
import java.util.Map;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

import org.talwood.marcworx.locparser.elements.CodeElement;
import org.talwood.marcworx.locparser.helpers.MarcTransformerHelper;

public class CodeElementMap {
    private Map<Integer, CodeElement> elementMap = new HashMap<Integer, CodeElement>();
    
    public CodeElementMap() {
        
    }
    
    public int determineLongestMarcCode() {
        int result = 0;
        for(CodeElement element : elementMap.values()) {
            if(result < MarcWorxStringHelper.length(element.getMarc())) {
                result = MarcWorxStringHelper.length(element.getMarc());
            }
        }
        return result;
    }

    public void addCodeElement(CodeElement value) {
        elementMap.put(MarcTransformerHelper.convertCodeToInteger(value.getMarc()), value);
    }
    
    public CodeElement findByID(Integer key) {
        return elementMap.get(key);
    }
}
