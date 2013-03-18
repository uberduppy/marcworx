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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.exception.MarcExceptionType;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.xmlgen.XMLBase;

public class ConvertMarcFileToMarcXml implements IMarcWorxThreadProcessor {

    private FileOutputStream fos = null;
    private String fileName = null;
            
    private ConvertMarcFileToMarcXml() {}
    
    public ConvertMarcFileToMarcXml(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public void processRecord(MarcRecord record) throws MarcException {
        if(fos != null) {
            try {
                XMLBase marcXml = MarcWorxDataHelper.convertSingletonMarcRecordToMarcXmlEntry(record); // MarcWorxDataHelper.convertSingletonMarcRecordToMarcXmlEntry(record);
                fos.write(marcXml.toXML().getBytes());
                fos.flush();
            } catch (IOException ex) {
                throw new MarcException(MarcExceptionType.WRITE_ERROR, ex);
            }
        }
    }

    @Override
    public void handleMarcException(MarcException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preProcess() throws MarcException {
        File file = new File(fileName);
        try {
            fos = new FileOutputStream(file);
            fos.write(buildXmlHeader());
            fos.flush();
        } catch (FileNotFoundException ex) {
            throw new MarcException(MarcExceptionType.OUTPUT_FILE_NOT_CREATED, ex);
        } catch (IOException ex) {
            throw new MarcException(MarcExceptionType.WRITE_ERROR, ex);
        }
    }

    @Override
    public void postProcess() throws MarcException {
        if(fos != null) {
            try {
                fos.write(buildXmlFooter());
                fos.flush();
                fos.close();
            } catch (IOException ex) {
                throw new MarcException(MarcExceptionType.WRITE_ERROR, ex);
            }
        }
        // Create the footer and close the file
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private byte[] buildXmlFooter() {
//</marc:collection>        
        return "</marc:collection>\r\n".getBytes();
    }
    private byte[] buildXmlHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        sb.append("<marc:collection xmlns:marc=\"http://www.loc.gov/MARC21/slim\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\">\r\n");
        return sb.toString().getBytes();
    }
}
