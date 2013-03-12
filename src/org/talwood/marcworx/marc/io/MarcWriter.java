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
