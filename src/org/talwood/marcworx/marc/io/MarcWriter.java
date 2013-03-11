package org.talwood.marcworx.marc.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.exception.MarcExceptionType;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;

public class MarcWriter {
    private String fileName = null;
    private BufferedOutputStream exportFile = null;

    public MarcWriter(String fileName) throws MarcException {
        this.fileName = fileName;
        try {
            exportFile = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
        } catch (FileNotFoundException ex) {
            throw new MarcException(MarcExceptionType.OUTPUT_FILE_NOT_CREATED);
        }
    }

    public MarcWriter(File file) throws MarcException {
        try {
            this.fileName = file.getCanonicalPath();
            exportFile = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException ex) {
            throw new MarcException(MarcExceptionType.OUTPUT_FILE_NOT_CREATED);
        }
    }

    public void writeRecord(MarcRecord record) throws IOException {
        if(exportFile != null) {
            exportFile.write(MarcWorxDataHelper.convertMARCToExportFormat(record));
        }
    }

    public void closeFile() {
        if(exportFile != null) {
            MarcWorxFileHelper.closeNoThrow(exportFile);
        }
    }
    
}
