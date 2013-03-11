package org.talwood.marcworx.threading;

import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.marc.containers.MarcRecord;

public interface IMarcWorxThreadProcessor {
    public void processRecord(MarcRecord record) throws MarcException;
    public void handleMarcException(MarcException ex);
    public void preProcess() throws MarcException;
    public void postProcess() throws MarcException;
}
