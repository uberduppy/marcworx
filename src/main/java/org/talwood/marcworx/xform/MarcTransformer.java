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
package org.talwood.marcworx.xform;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;
import org.talwood.marcworx.marc.containers.MarcRecord;

public class MarcTransformer {

    private static MarcTransformer internalTransformer = null;
    private Transformer modsTransformer = null;
    private Transformer dcRdfTransformer = null;
    private Transformer dcSrwTransformer = null;
    private XMLFormatter xmlFormatter = null;

    private MarcTransformer() {}
    
    public static MarcTransformer getTransformer() throws ConstraintException {
        if(internalTransformer == null) {
            internalTransformer = new MarcTransformer();
            internalTransformer.loadAllFromResources();
        }
        return internalTransformer;
    }
    
    private void loadAllFromResources() throws ConstraintException {
        loadModsTransformer("org/talwood/marcworx/data/marctomods.xsl");
        loadDublinCoreRdfTransformer("org/talwood/marcworx/data/dc_rdf.xsl");
        loadDublinCoreSrwTransformer("org/talwood/marcworx/data/dc_srw.xsl");
        xmlFormatter = new XMLFormatter();
        
    }

    private void loadModsTransformer(String className) throws ConstraintException {
        try {
            String fileName = MarcWorxFileHelper.locateResource(getClass(), className);
            InputStream is = new FileInputStream(fileName);

            StreamSource xsltSource= new StreamSource(is);
            TransformerFactory factory = TransformerFactory.newInstance();
            modsTransformer = factory.newTransformer(xsltSource);
            modsTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
            modsTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            modsTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");            
        } catch (TransformerConfigurationException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        } catch (IOException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        }
    }

    private void loadDublinCoreRdfTransformer(String className) throws ConstraintException {
        try {
            String fileName = MarcWorxFileHelper.locateResource(getClass(), className);
            InputStream is = new FileInputStream(fileName);

            StreamSource xsltSource= new StreamSource(is);
            TransformerFactory factory = TransformerFactory.newInstance();
            dcRdfTransformer = factory.newTransformer(xsltSource);
            dcRdfTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
            dcRdfTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            dcRdfTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");            
        } catch (TransformerConfigurationException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        } catch (IOException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        }
    }
    
    private void loadDublinCoreSrwTransformer(String className) throws ConstraintException {
        try {
            String fileName = MarcWorxFileHelper.locateResource(getClass(), className);
            InputStream is = new FileInputStream(fileName);

            StreamSource xsltSource= new StreamSource(is);
            TransformerFactory factory = TransformerFactory.newInstance();
            dcSrwTransformer = factory.newTransformer(xsltSource);
            dcSrwTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
            dcSrwTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            dcSrwTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");            
        } catch (TransformerConfigurationException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        } catch (IOException ex) {
            throw new ConstraintException(ConstraintExceptionType.INPUT_FILE_NOT_FOUND, ex);
        }
    }

    public String translateMarcToMods(MarcRecord record) throws TransformerException, ConstraintException {
        return translateMarcToMods(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
    }
    public String translateMarcToMods(String marcXml) throws TransformerException, ConstraintException {
        String result = null;
        StreamSource xmlSource = new StreamSource(new StringReader(marcXml));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StreamResult xmlResult = new StreamResult(os);
        if(modsTransformer != null) {
            modsTransformer.transform(xmlSource, xmlResult);
            result = os.toString();
        }
        if(result != null) {
            result = xmlFormatter.format(result);
        }
        return result;
    }
    
    public String translateMarcToDublinCoreRdf(MarcRecord record) throws TransformerException, ConstraintException {
        return translateMarcToDublinCoreRdf(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
    }
    public String translateMarcToDublinCoreRdf(String marcXml) throws TransformerException, ConstraintException {
        String result = null;
        StreamSource xmlSource = new StreamSource(new StringReader(marcXml));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StreamResult xmlResult = new StreamResult(os);
        if(dcRdfTransformer != null) {
            dcRdfTransformer.transform(xmlSource, xmlResult);
            result = os.toString();
        }
        if(result != null) {
            result = xmlFormatter.format(result);
        }
        return result;
    }
    
    public String translateMarcToDublinCoreSrw(MarcRecord record) throws TransformerException, ConstraintException {
        return translateMarcToDublinCoreSrw(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
    }
    public String translateMarcToDublinCoreSrw(String marcXml) throws TransformerException, ConstraintException {
        String result = null;
        StreamSource xmlSource = new StreamSource(new StringReader(marcXml));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StreamResult xmlResult = new StreamResult(os);
        if(dcSrwTransformer != null) {
            dcSrwTransformer.transform(xmlSource, xmlResult);
            result = os.toString();
        }
        if(result != null) {
            result = xmlFormatter.format(result);
        }
        return result;
    }
    
    
}
