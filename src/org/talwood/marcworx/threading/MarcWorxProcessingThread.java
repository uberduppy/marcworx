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
