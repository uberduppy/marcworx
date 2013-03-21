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

package org.talwood.marcworx.innovation.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.talwood.marcworx.innovation.containers.listobjects.DataTransformerElement;

/**
 *
 * @author twalker
 */
public class TransformerGeneralHelper {

    public static List<DataTransformerElement> invokeDataTransformerElementListGetterOnField(Field fld, Object objToInvokeOn) throws ConstraintException {
        List<DataTransformerElement> result = null;
        try {
            String methodName = makeMethodName("get", fld);
            Method m = objToInvokeOn.getClass().getMethod(methodName, (Class[])null);
            result = (List<DataTransformerElement>) m.invoke(objToInvokeOn, (Object[])null);
        } catch (SecurityException ex) {
            throw new ConstraintException(ConstraintExceptionType.SECURITY_EXCEPTION, "Unable to locate getter methods for " + fld.getName());
        } catch (NoSuchMethodException ex) {
            throw new ConstraintException(ConstraintExceptionType.NO_SUCH_METHOD, "No getter methods for " + fld.getName());
        } catch (IllegalArgumentException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ARGUMENT, "Invalid argument for " + fld.getName());
        } catch (IllegalAccessException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ACCESS, "Invalid argument for " + fld.getName());
        } catch (InvocationTargetException ex) {
            throw new ConstraintException(ConstraintExceptionType.INVOCATION_TARGET, "Invalid argument for " + fld.getName());
        }
        return result;
    }
    
    public static String invokeStringGetterOnField(Field fld, Object objToInvokeOn) throws ConstraintException {
        String result = null;
        try {
            String methodName = makeMethodName("get", fld);
            Method m = objToInvokeOn.getClass().getMethod(methodName, (Class[])null);
            result = (String) m.invoke(objToInvokeOn, (Object[])null);
        } catch (SecurityException ex) {
            throw new ConstraintException(ConstraintExceptionType.SECURITY_EXCEPTION, "Unable to locate getter methods for " + fld.getName());
        } catch (NoSuchMethodException ex) {
            throw new ConstraintException(ConstraintExceptionType.NO_SUCH_METHOD, "No getter methods for " + fld.getName());
        } catch (IllegalArgumentException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ARGUMENT, "Invalid argument for " + fld.getName());
        } catch (IllegalAccessException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ACCESS, "Invalid argument for " + fld.getName());
        } catch (InvocationTargetException ex) {
            throw new ConstraintException(ConstraintExceptionType.INVOCATION_TARGET, "Invalid argument for " + fld.getName());
        }
        return result;
    }
    
    public static String makeMethodName(String prefix, Field fld) {
        StringBuffer buffer = new StringBuffer( fld.getName() );
        buffer.setCharAt( 0, Character.toUpperCase( buffer.charAt(0) ) );
        buffer.insert( 0, prefix);
        return buffer.toString();
    }

    public static void invokeStringSetterOnField(Field fld, Object objToInvokeOn, String dataToSet) throws ConstraintException {
        try {
            String setMethodName = makeMethodName("set", fld);
            Method str = objToInvokeOn.getClass().getMethod(setMethodName, new Class[] { String.class} );
            str.invoke(objToInvokeOn, new Object[] { dataToSet } );
        } catch (SecurityException ex) {
            throw new ConstraintException(ConstraintExceptionType.SECURITY_EXCEPTION, "Unable to locate setter methods for " + fld.getName());
        } catch (NoSuchMethodException ex) {
            throw new ConstraintException(ConstraintExceptionType.NO_SUCH_METHOD, "No getter methods for " + fld.getName());
        } catch (IllegalArgumentException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ARGUMENT, "Invalid argument for " + fld.getName());
        } catch (IllegalAccessException ex) {
            throw new ConstraintException(ConstraintExceptionType.ILLEGAL_ACCESS, "Invalid argument for " + fld.getName());
        } catch (InvocationTargetException ex) {
            throw new ConstraintException(ConstraintExceptionType.INVOCATION_TARGET, "Invalid argument for " + fld.getName());
        }
    }
    
}
