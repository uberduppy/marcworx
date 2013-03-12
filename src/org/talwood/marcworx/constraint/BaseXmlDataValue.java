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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.talwood.marcworx.annotation.PostProcess;
import org.talwood.marcworx.annotation.PreProcess;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;

public abstract class BaseXmlDataValue {
    public abstract void updateMembers() throws ConstraintException;
    
    public void update() throws ConstraintException {
        // Call pre processing for data if necessary
        invokePreProcess();
        
        // Call constraint checking
        ConstraintChecker.checkConstriants(this);
        
        updateMembers();
        
        // Call post processing
        invokePostProcess();
    }
    
    private void invokePreProcess() throws ConstraintException {
        for(Method method : getClass().getMethods()) {
            if(method.isAnnotationPresent(PreProcess.class)) {
                invokeAnnotatedMethod(method);
            }
        }
    } 
    
    private void invokePostProcess() throws ConstraintException {
        for(Method method : getClass().getMethods()) {
            if(method.isAnnotationPresent(PostProcess.class)) {
                invokeAnnotatedMethod(method);
            }
        }
    } 
    
    private void invokeAnnotatedMethod(Method method) throws ConstraintException {
        try {
            method.invoke(this, (Object[])null);
        } catch (IllegalAccessException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ACCESS, ex);
        } catch (IllegalArgumentException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ARGUMENT, ex);
        } catch (InvocationTargetException ex) {
            throw new ConstraintException(ConstraintExceptionType.INVOCATION_TARGET, ex);
        }
    }
    
    
}
