package org.talwood.marcworx.xmlgen;

import java.util.Iterator;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;

public class XMLBuilder extends XMLBase {
    private String encoding = MarcWorxFileHelper.ENCODING_ISO_8859_1;

    private XMLBuilder() {
        super();
    }

    public XMLBuilder(String name) {
        super(name);
    }

    public XMLBuilder(String name, String encoding) {
        super(name);
        this.encoding = encoding;
    }

    public XMLEntry createEntry(String name) {
        XMLEntry entry = new XMLEntry(1);
        if (entry != null) {
            addXMLEntry(name, entry);
        }
        return entry;
    }

    @Override
    public String toString() {
        return toXML();
    }

    @Override
    public String toXML() {
        StringBuilder xmlData = new StringBuilder();

        xmlData.append("<?xml version=\"1.0\" encoding=\"").append(encoding).append("\"?>\r\n");

        xmlData.append(buildAttributeLine(""));
        xmlData.append(buildParameterLine(""));

        Iterator<XMLBase> iter = getXmlEntries().iterator();
        while(iter.hasNext()) {
            xmlData.append(iter.next().toXML());
        }

        if (!getParameterList().isEmpty() || !getXmlEntries().isEmpty()) {
            xmlData.append("</").append(getName()).append(">\r\n");
        }

        return xmlData.toString();

    }

}
