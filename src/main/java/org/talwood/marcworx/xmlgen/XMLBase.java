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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.helpers.TupleContainer;

public abstract class XMLBase {
    private List<XMLBase> xmlEntries = new ArrayList<XMLBase>();
    private List<TupleContainer<String, String>> parameterList = new ArrayList<TupleContainer<String, String>>();
    private List<TupleContainer<String, String>> attributeList = new ArrayList<TupleContainer<String, String>>();
    private String name;
    private String data;

    public XMLBase() {

    }

    public XMLBase(String name) {
        this.name = name;
    }

    public abstract String toXML();

/*----------------------------------------------------------------------------*/
    public void setAttribute(String optionName, char value) {
        setAttribute(optionName, String.valueOf(value));
    }
    public void setAttribute(String optionName, int value) {
        setAttribute(optionName, String.valueOf(value));
    }
    public void setAttribute(String optionName, String value) {
        if (value != null) {
            String modValue = MarcWorxStringHelper.removeHTMLTags(value);
            modValue = MarcWorxStringHelper.replaceStringWithString(modValue, "\r", " ");
            modValue = MarcWorxStringHelper.replaceStringWithString(modValue, "\n", " ");
            modValue = MarcWorxStringHelper.removeHTMLTags(modValue);
            modValue = MarcWorxStringHelper.removeChars(modValue, "\"");
            attributeList.add(new TupleContainer<String, String>(optionName, modValue));
        }
    }

/*----------------------------------------------------------------------------*/
    public void addAllToParameterList(String paramName, List<String> values) {
        Iterator<String> iter = values.iterator();
        while(iter.hasNext()) {
            setParameter(paramName, iter.next());
        }
    }
/*----------------------------------------------------------------------------*/
    public void setParameter(String paramName, String value) {
        if (paramName != null && value != null) {
            String modValue = MarcWorxStringHelper.removeHTMLTags(value);
            modValue = MarcWorxStringHelper.replaceStringWithString(modValue, "\r", " ");
            modValue = MarcWorxStringHelper.replaceStringWithString(modValue, "\n", " ");
            modValue = MarcWorxStringHelper.removeChars(modValue, "\"");
            boolean needsCData = MarcWorxStringHelper.needsCData(modValue);
            if (value.indexOf("<![CDATA[") != -1 || !needsCData) {
                parameterList.add(new TupleContainer<String, String>( paramName, modValue));
            } else {
                parameterList.add(new TupleContainer<String, String>(paramName, "<![CDATA[" + modValue + "]]>"));
            }
        }
    }

    public String buildAttributeLine(String indentionString) {
        StringBuilder retVal = new StringBuilder();
        retVal.append(indentionString).append("<").append(name);
        Iterator<TupleContainer<String, String>> iter = attributeList.iterator();
        while(iter.hasNext()) {
            TupleContainer<String, String> ssl = iter.next();
            String param = ssl.getLeftSide();
            String value = ssl.getRightSide();
            if(MarcWorxStringHelper.isNotEmpty(value)) {
                retVal.append(' ').append(param).append("=\"").append(value)
                    .append("\"");
            }
        }
        if(parameterList.isEmpty() && xmlEntries.isEmpty() && MarcWorxStringHelper.isEmpty(data)) {
            retVal.append("/");
        }
        retVal.append(">\r\n");
        return retVal.toString();
    }

    public String buildParameterLine(String indentionString) {
        StringBuilder retVal = new StringBuilder();
        Iterator<TupleContainer<String, String>> iter = parameterList.iterator();
        while(iter.hasNext()) {
            TupleContainer<String, String> ssl = iter.next();
            String param = ssl.getLeftSide();
            String value = ssl.getRightSide();
            if(MarcWorxStringHelper.isNotEmpty(value)) {
                retVal.append(indentionString).append("   <").append(param).append('>').append(value)
                    .append("</").append(param).append(">\r\n");
            }
        }
        return retVal.toString();
    }




    protected void addXMLEntry(String name, XMLBase entry) {
        xmlEntries.add(entry);
        entry.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<TupleContainer<String, String>> getAttributeList() {
        return attributeList;
    }

    public List<TupleContainer<String, String>> getParameterList() {
        return parameterList;
    }

    public List<XMLBase> getXmlEntries() {
        return xmlEntries;
    }

    

}
