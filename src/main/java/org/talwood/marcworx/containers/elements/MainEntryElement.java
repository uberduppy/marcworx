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

package org.talwood.marcworx.containers.elements;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.DataWorkConstants;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="MainEntry")
public class MainEntryElement extends BaseTagDataElement implements Serializable {
    
    private String type;
    private String name;
    private String numeration;
    private String title;
    private String date;
    private String relatorTerm;
    
    public MainEntryElement() {
        
    }
    
    public MainEntryElement(MarcTag tag) {
        parseTag(tag);
    }
    
    private void parseTag(MarcTag tag) {
        switch(tag.getTagNumber()) {
            case 100:
                type="Personal";
                setName(MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP_NO_PERIOD));
                setNumeration(MarcWorxDataHelper.pullDataFromSubfield(tag, 'b', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
                setTitle(MarcWorxDataHelper.pullAppendedDataFromSubfields(tag, "c", " ", DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
                setDate(MarcWorxDataHelper.pullDataFromSubfield(tag, 'd', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
                setRelatorTerm(MarcWorxDataHelper.pullAppendedDataFromSubfields(tag, "e", " ", DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
                break;
            case 110:
                type="Corporate";
                setName(MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP_NO_PERIOD));
                break;
            case 111:
                type="Meeting";
                setName(MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP_NO_PERIOD));
                break;
            case 130:
                type="Uniform Title";
                setName(MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP_NO_PERIOD));
                break;
        }
    }

    @XmlAttribute(name="type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name="Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setValid(MarcWorxStringHelper.isNotEmpty(name));
    }

    @XmlElement(name="Numeration")
    public String getNumeration() {
        return numeration;
    }

    public void setNumeration(String numeration) {
        this.numeration = numeration;
    }

    @XmlElement(name="Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name="Date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name="Relator")
    public String getRelatorTerm() {
        return relatorTerm;
    }

    public void setRelatorTerm(String relatorTerm) {
        this.relatorTerm = relatorTerm;
    }
    
    
    
}
