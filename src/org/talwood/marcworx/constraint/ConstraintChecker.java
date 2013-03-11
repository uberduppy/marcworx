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
