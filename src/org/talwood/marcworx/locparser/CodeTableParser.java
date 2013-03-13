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
import org.talwood.marcworx.helpers.TupleContainer;
import org.talwood.marcworx.locparser.containers.CodeElementMap;
import org.talwood.marcworx.locparser.elements.DiacriticEntryElement;
import org.talwood.marcworx.locparser.elements.DiacriticLetterElement;
import org.talwood.marcworx.locparser.elements.DiacriticLookupContainer;
import org.talwood.marcworx.locparser.elements.DiacriticLookupElement;
import org.talwood.marcworx.locparser.helpers.MarcTransformerHelper;

public class CodeTableParser {
    
    private static CodeTableParser internalParser = null;
            
    private CodeTableContainer container;
    private DiacriticLookupContainer diacContainer;
    
    private Map<Integer, CodeElementMap> codeMap = new HashMap<Integer, CodeElementMap>();
    private Map<Integer, Map<Integer, Map<Integer, DiacriticLetterElement>>> diacMap = new HashMap<Integer, Map<Integer, Map<Integer, DiacriticLetterElement>>>();
    
    private List<CodeElement> elements = new ArrayList<CodeElement>();
    
    private CodeTableParser() {}
    
    public static CodeTableParser getCodeTableParser() throws ConstraintException {
        if(internalParser == null) {
            internalParser = new CodeTableParser();
            internalParser.loadFromResources("org/talwood/marcworx/data/codetables.xml");
            internalParser.loadLookupFromResources("org/talwood/marcworx/data/diaclookup.xml");
            internalParser.populateElements();
            internalParser.populateCodeMap();
            internalParser.populateDiacriticsMap();
        }
        return internalParser;
    }
    
    public CodeElementMap findListForCodeTable(int codeTable) {
        return codeMap.get(new Integer(codeTable));
    }
    
    public int findCodeForDiacritic(int isoCode, int firstCode, int secondCode) {
        int code = 0;
        Map<Integer, Map<Integer, DiacriticLetterElement>> isoMap = diacMap.get(new Integer(isoCode));
        if(isoMap != null) {
            Map<Integer, DiacriticLetterElement> firstMap = isoMap.get(new Integer(firstCode));
            if(firstMap != null) {
                DiacriticLetterElement element = firstMap.get(new Integer(secondCode));
                if(element != null) {
                    code = Integer.parseInt(element.getReplacement(), 16);
                }
            }
        }
        return code;
    }

    private void populateDiacriticsMap() {
        for(DiacriticLookupElement element : diacContainer.getElements()) {
            int iso = Integer.parseInt(element.getIsoCode(), 16);
            Map<Integer, Map<Integer, DiacriticLetterElement>> isomap = diacMap.get(new Integer(iso));
            if(isomap == null) {
                isomap = new HashMap<Integer, Map<Integer, DiacriticLetterElement>>();
                diacMap.put(new Integer(iso), isomap);
            }
            for(DiacriticEntryElement entry : element.getEntries()) {
                int first = Integer.parseInt(entry.getFirst(), 16);
                Map<Integer, DiacriticLetterElement> firstmap = isomap.get(new Integer(first));
                if(firstmap == null) {
                    firstmap = new HashMap<Integer, DiacriticLetterElement>();
                    isomap.put(new Integer(first), firstmap);
                }
                for(DiacriticLetterElement letters : entry.getLetters()) {
                    int second = Integer.parseInt(letters.getSecond(), 16);
                    if(firstmap.get(new Integer(second)) == null) {
                        firstmap.put(new Integer(second), letters);
                    }
                }
            }
            
        }
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

    private void loadLookupFromResources(String className) throws ConstraintException {
        try {
            JAXBContext context = JAXBContext.newInstance(DiacriticLookupContainer.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            diacContainer = (DiacriticLookupContainer)unmarshaller.unmarshal(findResource(className));
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
