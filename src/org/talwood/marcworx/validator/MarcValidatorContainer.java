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
package org.talwood.marcworx.validator;

import org.talwood.marcworx.validator.elements.MarcValidatorValue;
import org.talwood.marcworx.validator.elements.MarcRulesOuterValue;
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
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.marc.enums.RecordType;

public class MarcValidatorContainer {

    private static MarcValidatorContainer internalContainer = null;
    
    private Map<FormatType, MarcValidatorValue> marcValidatorMap = new EnumMap<FormatType, MarcValidatorValue>(FormatType.class);
    private List<MarcValidatorValue> marcValidatorList = new ArrayList<MarcValidatorValue>();
    
    public static MarcValidatorContainer getValidator() throws ConstraintException {
        if(internalContainer == null) {
            internalContainer = new MarcValidatorContainer();
            internalContainer.loadAllFromResources();
        }
        return internalContainer;
    }
    
    private void loadAllFromResources() throws ConstraintException {
        loadFromResources("org/talwood/marcworx/data/bibdefs.xml");
        loadFromResources("org/talwood/marcworx/data/authdefs.xml");
        loadFromResources("org/talwood/marcworx/data/holddefs.xml");
        loadFromResources("org/talwood/marcworx/data/commdefs.xml");
        loadFromResources("org/talwood/marcworx/data/classdefs.xml");
        
    }

    private void loadFromResources(String className) throws ConstraintException {
        try {
            JAXBContext context = JAXBContext.newInstance(MarcRulesOuterValue.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MarcRulesOuterValue outerVal = (MarcRulesOuterValue)unmarshaller.unmarshal(findResource(className));
            
            for(MarcValidatorValue val : outerVal.getMarcValidatorList()) {
                val.update();
                marcValidatorList.add(val);
                FormatType type = FormatType.findByFormatDescription(val.getCode());
                marcValidatorMap.put(type, val);
            }
            
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

    public MarcValidatorValue getElementForFormatType(FormatType formatType) {
        MarcValidatorValue e = null;
        e = marcValidatorMap.get(formatType);
        return e;
    }

    public List<MarcValidatorValue> getMarcValidatorList() {
        return marcValidatorList;
    }
    
    public MarcValidatorValue getElementForRecord(MarcRecord record) {
        MarcValidatorValue e = null;
        RecordType rt = RecordType.findByRecordTypeCode(record.getLeader().getRecordType(), RecordType.RECORD_TYPE_UNKNOWN);
        FormatType ft = rt.getFormatType();
        e = marcValidatorMap.get(ft);
        if(e == null) {
            e = marcValidatorList.get(0); // Default to bib.
        }
        return e;
    }
    
}
