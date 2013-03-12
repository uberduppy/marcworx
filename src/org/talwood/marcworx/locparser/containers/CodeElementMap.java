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
