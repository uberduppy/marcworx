package org.talwood.marcworx.threading;

import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.marc.containers.MarcRecord;

public class SampleMarcWriterProcessor implements IMarcWorxThreadProcessor {
    public SampleMarcWriterProcessor() {
        
    }

    @Override
    public void processRecord(MarcRecord record) throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void handleMarcException(MarcException ex) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void preProcess() throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postProcess() throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
