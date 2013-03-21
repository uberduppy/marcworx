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

package org.talwood.marcworx.innovation.xformers;

import java.lang.reflect.Field;
import java.util.List;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.helpers.TupleContainer;
import org.talwood.marcworx.innovation.annotation.CompositeFieldString;
import org.talwood.marcworx.innovation.annotation.DataTransformerField;
import org.talwood.marcworx.innovation.annotation.NonFilingIndicatorFieldString;
import org.talwood.marcworx.innovation.annotation.NonRepeatableFieldString;
import org.talwood.marcworx.innovation.annotation.RepeatableFieldString;
import org.talwood.marcworx.innovation.containers.BaseTransformerElement;
import org.talwood.marcworx.innovation.containers.listobjects.DataTransformerElement;
import org.talwood.marcworx.innovation.helpers.TransformerGeneralHelper;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public abstract class BaseTransformer {
    
    public abstract <T extends BaseTransformerElement> T buildElement();
    
    public abstract void doSpecialProcessing(BaseTransformerElement element);
    
    protected MarcTag tag;

    public BaseTransformer(MarcTag tag) {
        this.tag = tag;
    }
    
    public <T extends BaseTransformerElement> T processTagData() throws ConstraintException {
        BaseTransformerElement element = buildElement();
        for(Field fld : element.getClass().getDeclaredFields()) {
            // Loop through each type of annotation and process it
            if(fld.isAnnotationPresent(NonRepeatableFieldString.class)) {
                processNonRepeatableFieldString(element, fld);
            }
            if(fld.isAnnotationPresent(RepeatableFieldString.class)) {
                processRepeatableFieldString(element, fld);
            }
            if(fld.isAnnotationPresent(NonFilingIndicatorFieldString.class)) {
                processNonFilingIndicatorFieldString(element, fld);
            }
            if(fld.isAnnotationPresent(CompositeFieldString.class)) {
                processCompositeFieldString(element, fld);
            }
            if(fld.isAnnotationPresent(DataTransformerField.class)) {
                processDataTransformerField(element, fld);
            }
            
        }
        
        doSpecialProcessing(element);
        
        return (T)element;
    }
    
    private void processDataTransformerField(BaseTransformerElement element, Field fld) throws ConstraintException {
        DataTransformerField dtf = fld.getAnnotation(DataTransformerField.class);
        List<DataTransformerElement> list = TransformerGeneralHelper.invokeDataTransformerElementListGetterOnField(fld, element);
        if(list != null) {
            for(MarcSubfield sub : tag.getSubfields(dtf.subfield())) {
                String fieldData = sub.getData();
                if(MarcWorxStringHelper.isNotEmpty(fieldData) && dtf.stripPunctuation()) {
                    if(MarcWorxStringHelper.isNotEmpty(dtf.leadingPunctuation())) {
                        fieldData = MarcWorxStringHelper.stripLeadingData(fieldData, dtf.leadingPunctuation());
                    }
                    if(MarcWorxStringHelper.isNotEmpty(dtf.trailingPunctuation())) {
                        fieldData = MarcWorxStringHelper.stripTrailingData(fieldData, dtf.trailingPunctuation());
                    }
                }
                if(MarcWorxStringHelper.isNotEmpty(fieldData)) {
                    list.add(new DataTransformerElement(fieldData));
                }
            }
        }
    }
    private void processCompositeFieldString(BaseTransformerElement element, Field fld) throws ConstraintException {
        CompositeFieldString cfs = fld.getAnnotation(CompositeFieldString.class);
        StringBuilder sb = new StringBuilder();
        for(MarcSubfield sub : tag.getSubfields(cfs.subfields())) {
            String fieldData = sub.getData();
            if(MarcWorxStringHelper.isNotEmpty(fieldData) && cfs.stripPunctuation()) {
                if(MarcWorxStringHelper.isNotEmpty(cfs.leadingPunctuation())) {
                    fieldData = MarcWorxStringHelper.stripLeadingData(fieldData, cfs.leadingPunctuation());
                }
                if(MarcWorxStringHelper.isNotEmpty(cfs.trailingPunctuation())) {
                    fieldData = MarcWorxStringHelper.stripTrailingData(fieldData, cfs.trailingPunctuation());
                }
            }
            if(sb.length() > 0 && fieldData.length() > 0) {
                sb.append(cfs.divider());
            }
            sb.append(fieldData);
            
        }
        String dataToAdd = sb.length() > 0 ? sb.toString() : null;
        TransformerGeneralHelper.invokeStringSetterOnField(fld, element, dataToAdd);
    }
    
    private void processNonFilingIndicatorFieldString(BaseTransformerElement element, Field fld) throws ConstraintException {
        NonFilingIndicatorFieldString nfifs = fld.getAnnotation(NonFilingIndicatorFieldString.class);
        char nonfiling = nfifs.indicatorToUse() == 1 ? tag.getFirstIndicator() : tag.getSecondIndicator();
    
        MarcSubfield suba = tag.getSubfield(nfifs.subfield(), 1);
        if(suba != null) {
            String subdata = suba.getData();
            TupleContainer<String, String> splitData = MarcWorxDataHelper.splitString(subdata, nonfiling);
            String fieldData = "";
            if(nfifs.before()) {
                fieldData = splitData.getLeftSide();
            } else {
                fieldData = splitData.getRightSide();
            }
            if(MarcWorxStringHelper.isEmpty(fieldData) && nfifs.stripPunctuation()) {
                if(MarcWorxStringHelper.isNotEmpty(nfifs.leadingPunctuation())) {
                    fieldData = MarcWorxStringHelper.stripLeadingData(fieldData, nfifs.leadingPunctuation());
                }
                if(MarcWorxStringHelper.isNotEmpty(nfifs.trailingPunctuation())) {
                    fieldData = MarcWorxStringHelper.stripTrailingData(fieldData, nfifs.trailingPunctuation());
                }
            }
            TransformerGeneralHelper.invokeStringSetterOnField(fld, element, fieldData);
        }
    }
    
    
    private void processNonRepeatableFieldString(BaseTransformerElement element, Field fld) throws ConstraintException {
        NonRepeatableFieldString nrfs = fld.getAnnotation(NonRepeatableFieldString.class);
        MarcSubfield sub = tag.getSubfield(nrfs.subfield(), 1);
        if(sub != null) {
            String subData = sub.getData();
            if(MarcWorxStringHelper.isNotEmpty(subData)) {
                if(nrfs.stripPunctuation()) {
                    if(MarcWorxStringHelper.isNotEmpty(nrfs.leadingPunctuation())) {
                        subData = MarcWorxStringHelper.stripLeadingData(subData, nrfs.leadingPunctuation());
                    }
                    if(MarcWorxStringHelper.isNotEmpty(nrfs.trailingPunctuation())) {
                        subData = MarcWorxStringHelper.stripTrailingData(subData, nrfs.trailingPunctuation());
                    }
                }
                TransformerGeneralHelper.invokeStringSetterOnField(fld, element, subData);
            }
        }
        
    }

    private void processRepeatableFieldString(BaseTransformerElement element, Field fld) throws ConstraintException {
        RepeatableFieldString rfs = fld.getAnnotation(RepeatableFieldString.class);
        List<MarcSubfield> subs = tag.getSubfields(rfs.subfield());
        StringBuilder data = new StringBuilder();
        for(MarcSubfield sub : subs) {
            String subData = sub.getData();
            if(MarcWorxStringHelper.isNotEmpty(subData)) {
                if(rfs.stripPunctuation()) {
                    if(MarcWorxStringHelper.isNotEmpty(rfs.leadingPunctuation())) {
                        subData = MarcWorxStringHelper.stripLeadingData(subData, rfs.leadingPunctuation());
                    }
                    if(MarcWorxStringHelper.isNotEmpty(rfs.trailingPunctuation())) {
                        subData = MarcWorxStringHelper.stripTrailingData(subData, rfs.trailingPunctuation());
                    }
                }
            }
            if(data.length() > 0 && subData.length() > 0) {
                data.append(rfs.divider());
            }
            data.append(subData);
        }
        String dataToAdd = data.length() > 0 ? data.toString() : null;
        TransformerGeneralHelper.invokeStringSetterOnField(fld, element, dataToAdd);
        
    }

    public MarcTag getTag() {
        return tag;
    }

    public void setTag(MarcTag tag) {
        this.tag = tag;
    }
}
