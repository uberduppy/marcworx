package org.talwood.marcworx.xform;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLFormatter {

    public XMLFormatter() {
        
    }
    
    public String format(String unformattedXml) throws ConstraintException {
        try {
            Document document = parseXmlFile(unformattedXml);
 
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(3);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
 
            return out.toString();
        } catch (IOException ex) {
            return "";
        }
 
    }
    
    private Document parseXmlFile(String in) throws ConstraintException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException ex) {
            throw new ConstraintException(ConstraintExceptionType.OUTPUT_FILE_NOT_CREATED, ex);
        } catch (SAXException ex) {
            throw new ConstraintException(ConstraintExceptionType.OUTPUT_FILE_NOT_CREATED, ex);
        } catch (IOException ex) {
            throw new ConstraintException(ConstraintExceptionType.OUTPUT_FILE_NOT_CREATED, ex);
        }
    }
    
}
