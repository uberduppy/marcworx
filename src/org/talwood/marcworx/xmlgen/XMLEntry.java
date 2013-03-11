package org.talwood.marcworx.xmlgen;

import java.util.Iterator;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.helpers.TupleContainer;

public class XMLEntry extends XMLBase {

    private int indention;

    public XMLEntry() {

    }

    public XMLEntry(int indention) {
        super();
        this.indention = indention;
    }

/*----------------------------------------------------------------------------*/
    public static XMLEntry createMainEntry(String name) {
        XMLEntry entry = new XMLEntry(1);
        entry.setName(name);
        return entry;
    }
/*----------------------------------------------------------------------------*/
    public XMLEntry createEntry(String name) {
        XMLEntry entry = new XMLEntry(indention + 1);
        if (entry != null && name != null) {
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
        String indentionString = gimmeIndentionString();

        if(MarcWorxStringHelper.isNotEmpty(getData())) {
            xmlData.append(indentionString).append("<").append(getName());
            Iterator<TupleContainer<String, String>> iter = getAttributeList().iterator();
            while(iter.hasNext()) {
                TupleContainer<String, String> ssl = iter.next();
                String param = ssl.getLeftSide();
                String value = ssl.getRightSide();
                if(MarcWorxStringHelper.isNotEmpty(value)) {
                    xmlData.append(' ').append(param).append("=\"").append(value)
                        .append("\"");
                }
            }
            xmlData.append(">");
            if(MarcWorxStringHelper.needsCData(getData())) {
                xmlData.append("<![CDATA[").append(getData()).append("]]>");
            } else {
                xmlData.append(getData());
            }
            xmlData.append("</").append(getName()).append(">\r\n");
        } else {
            xmlData.append(buildAttributeLine(indentionString));
            if(MarcWorxStringHelper.isNotEmpty(getData())) {
                xmlData.append(getData());
            }
            xmlData.append(buildParameterLine(indentionString));

            Iterator<XMLBase> iter = getXmlEntries().iterator();
            while(iter.hasNext()) {
                xmlData.append(iter.next().toXML());
            }

            if (!getParameterList().isEmpty() || !getXmlEntries().isEmpty() || MarcWorxStringHelper.isNotEmpty(getData())) {
                xmlData.append(indentionString).append("</").append(getName()).append(">\r\n");
            }
        }
        return xmlData.toString();

    }

    public String gimmeIndentionString() {
        StringBuilder indentionString = new StringBuilder();
        for (int i = 0; i < indention; i++) {
            indentionString.append("   ");
        }
        return indentionString.toString();
    }


}
