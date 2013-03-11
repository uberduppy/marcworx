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
