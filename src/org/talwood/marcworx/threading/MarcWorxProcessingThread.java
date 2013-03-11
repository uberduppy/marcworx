package org.talwood.marcworx.threading;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;
import org.talwood.marcworx.marc.io.MarcReader;

public class MarcWorxProcessingThread extends Thread {

    private MarcReader reader = null;
    private IMarcWorxThreadProcessor processor = null;
    private boolean keepAlive = true;
    
    public MarcWorxProcessingThread(IMarcWorxThreadProcessor processor, MarcReader reader) {
        this.processor = processor;
        this.reader = reader;
    }
    
    public MarcWorxProcessingThread(IMarcWorxThreadProcessor processor, File file) throws MarcException {
        this.processor = processor;
        this.reader = new MarcReader(file);
    }
    
    @Override
    public void run() {
        keepAlive = true;
        if(reader != null && processor != null) {
            MarcRecord record;
            try {
                processor.preProcess();
                record = reader.getNextRecord();
                while(isAlive() && keepAlive && record.getReadStatus() == MarcFileReadStatus.VALID) {
                    processor.processRecord(record);
                    record = reader.getNextRecord();
                }
                processor.postProcess();
            } catch (MarcException ex) {
                processor.handleMarcException(ex);
            }
        }
    }
    
    
}
