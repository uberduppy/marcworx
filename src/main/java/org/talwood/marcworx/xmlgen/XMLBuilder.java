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
