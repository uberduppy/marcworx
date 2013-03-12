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
package org.talwood.marcworx.constraint;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.talwood.marcworx.annotation.MaxLength;
import org.talwood.marcworx.annotation.RootConstraint;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;

public class ConstraintChecker {
    public static void checkConstriants(BaseXmlDataValue obj) throws ConstraintException {
        for(Method method : obj.getClass().getMethods()) {
            for(Annotation anno : method.getAnnotations()) {
                if(isConstraint(anno)) {
                    try {
                        String value = (String)method.invoke(obj, (Object[])null);
                        if(anno instanceof MaxLength) {
                            int max = ((MaxLength)anno).value();
                            if (value != null && value.length() > max) {
                                throw new ConstraintException(ConstraintExceptionType.CONSTRIANT, "Length of \"" + value + "\" is larger than " + max);
                            }
                                
                        }
                    } catch (IllegalAccessException ex) {
                        throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ACCESS, ex);
                    } catch (IllegalArgumentException ex) {
                        throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ARGUMENT, ex);
                    } catch (InvocationTargetException ex) {
                        throw new ConstraintException(ConstraintExceptionType.INVOCATION_TARGET, ex);
                    }
                }
            }
        }
    }
    
    public static boolean isConstraint(Annotation annotation) {
        if(annotation == null) {
            throw new IllegalArgumentException("annotation cannot be null");
        }
        return annotation.annotationType().isAnnotationPresent(RootConstraint.class);
    }
}
