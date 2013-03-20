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
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.innovation.annotation.NonRepeatableFieldString;
import org.talwood.marcworx.innovation.annotation.RootMarcFieldAnnotation;
import org.talwood.marcworx.innovation.containers.BaseTransformerElement;
import org.talwood.marcworx.innovation.helpers.TransformerGeneralHelper;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public abstract class BaseTransformer {
    
    public abstract <T extends BaseTransformerElement> T buildElement();
    
    private MarcTag tag;

    public BaseTransformer(MarcTag tag) {
        this.tag = tag;
    }
    
    public <T extends BaseTransformerElement> T processTagData() throws ConstraintException {
        BaseTransformerElement element = buildElement();
        for(Field fld : element.getClass().getDeclaredFields()) {
            if(fld.isAnnotationPresent(RootMarcFieldAnnotation.class)) {
                // Loop through each type of annotation and process it
                processNonRepeatableFieldString(fld);
            }
            
        }
        
        return (T)element;
    }
    
    private void processNonRepeatableFieldString(Field fld) throws ConstraintException {
        if(fld.isAnnotationPresent(NonRepeatableFieldString.class)) {
            NonRepeatableFieldString nrfs = fld.getAnnotation(NonRepeatableFieldString.class);
            String getterResult = TransformerGeneralHelper.invokeStringGetterOnField(fld, this);
            
        }
        
    }

    public MarcTag getTag() {
        return tag;
    }

    public void setTag(MarcTag tag) {
        this.tag = tag;
    }
}
