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
package org.talwood.marcworx.locparser;

import org.talwood.marcworx.locparser.elements.CharacterSetElement;
import org.talwood.marcworx.locparser.elements.CodeTableContainer;
import org.talwood.marcworx.locparser.elements.CodeElement;
import org.talwood.marcworx.locparser.elements.GroupingElement;
import org.talwood.marcworx.locparser.elements.CodeTableElement;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;
import org.talwood.marcworx.locparser.containers.CodeElementMap;
import org.talwood.marcworx.locparser.helpers.MarcTransformerHelper;

public class CodeTableParser {
    
    private static CodeTableParser internalParser = null;
            
    private CodeTableContainer container;
    
    private Map<Integer, CodeElementMap> codeMap = new HashMap<Integer, CodeElementMap>();
    
    private List<CodeElement> elements = new ArrayList<CodeElement>();
    
    private CodeTableParser() {}
    
    public static CodeTableParser getCodeTableParser() throws ConstraintException {
        if(internalParser == null) {
            internalParser = new CodeTableParser();
            internalParser.loadFromResources("org/talwood/marcworx/data/codetables.xml");
            internalParser.populateElements();
            internalParser.populateCodeMap();
        }
        return internalParser;
    }
    
    public CodeElementMap findListForCodeTable(int codeTable) {
        return codeMap.get(new Integer(codeTable));
    }
    
    private void populateCodeMap() {
        for(CodeTableElement cte : container.getCodeTables()) {
            for(CharacterSetElement cse : cte.getCharSets()) {
                CodeElementMap thisList = codeMap.get(MarcTransformerHelper.convertCodeToInteger(cse.getIsoCode()));
                if(thisList == null) {
                    thisList = new CodeElementMap();
                    codeMap.put(MarcTransformerHelper.convertCodeToInteger(cse.getIsoCode()), thisList);
                }
                // Add every code element
                for(CodeElement ce : cse.getCodeList()) {
                    thisList.addCodeElement(ce);
                }
                // Add every code element in any groupings
                for(GroupingElement ge : cse.getGroupingList()) {
                    for(CodeElement ce : ge.getCodeList()) {
                        thisList.addCodeElement(ce);
                    }
                }
            }
        }
    }
    
    private void populateElements() {
        elements.clear();
        for(CodeTableElement cte : container.getCodeTables()) {
            for(CharacterSetElement cse : cte.getCharSets()) {
                for(CodeElement ce : cse.getCodeList()) {
                    elements.add(ce);
                }
                for(GroupingElement ge : cse.getGroupingList()) {
                    for(CodeElement ce : ge.getCodeList()) {
                        elements.add(ce);
                    }
                }
            }
        }
    }
    
    private void loadFromResources(String className) throws ConstraintException {
        try {
            JAXBContext context = JAXBContext.newInstance(CodeTableContainer.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            container = (CodeTableContainer)unmarshaller.unmarshal(findResource(className));
        } catch (JAXBException ex) {
            throw new ConstraintException(ConstraintExceptionType.JAXB_EXCEPTION, ex);
        }
    }

    private InputStream findResource(String location) {
        InputStream is = null;
        try {
            String fileName = MarcWorxFileHelper.locateResource(getClass(), location);
            is = new FileInputStream(fileName);
        } catch (Exception ex) {
            // TODO Change this, perhaps a constraint exception?
            System.out.println("Error loading resource");
            ex.printStackTrace(System.out);
        }
        return is;
    }

    public CodeTableContainer getContainer() {
        return container;
    }

    public List<CodeElement> getElements() {
        return elements;
    }
    
    

}
