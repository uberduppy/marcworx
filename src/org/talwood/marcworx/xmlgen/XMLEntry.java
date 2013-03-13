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
