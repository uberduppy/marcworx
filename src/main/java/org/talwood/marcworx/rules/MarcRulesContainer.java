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
package org.talwood.marcworx.rules;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;
import org.talwood.marcworx.marc.enums.FormatType;

public class MarcRulesContainer {

    private static MarcRulesContainer internalContainer = null;
    
    private Map<FormatType, MainDefinitionValue> tagDefinitionMap = new EnumMap<FormatType, MainDefinitionValue>(FormatType.class);
    private List<MainDefinitionValue> tagDefinitionList = new ArrayList<MainDefinitionValue>();

    public static MarcRulesContainer getValidator() throws ConstraintException {
        if(internalContainer == null) {
            internalContainer = new MarcRulesContainer();
            internalContainer.loadAllFromResources();
        }
        return internalContainer;
    }
    
    private void loadAllFromResources() throws ConstraintException {
        loadBibRulesFromResources(FormatType.BIBLIOGRAPHIC, "org/talwood/marcworx/data/bibrules.xml");
        loadBibRulesFromResources(FormatType.AUTHORITY, "org/talwood/marcworx/data/authrules.xml");
        
    }

    private void loadBibRulesFromResources(FormatType mapSection, String className) throws ConstraintException {
        try {
            JAXBContext context = JAXBContext.newInstance(MainDefinitionValue.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MainDefinitionValue val = (MainDefinitionValue)unmarshaller.unmarshal(findResource(className));

            tagDefinitionList.add(val);
            tagDefinitionMap.put(mapSection, val);
            
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

    
    
    public MainDefinitionValue getRulesForFormatType(FormatType formatType) {
        MainDefinitionValue e = null;
        e = tagDefinitionMap.get(formatType);
        return e;
    }

    
}
