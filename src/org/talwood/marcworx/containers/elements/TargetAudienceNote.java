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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.containers.enums.TargetAudienceEnum;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="TargetAudience")
public class TargetAudienceNote extends BaseTagDataElement implements Serializable {
    
    private String description;
    private String targetAudienceNote;
    private String source;
    
    public TargetAudienceNote() {}
    
    public TargetAudienceNote(MarcTag tag) {
        parseTag(tag);
    }
    
    private void parseTag(MarcTag tag) {
        TargetAudienceEnum e = TargetAudienceEnum.findByIndicatorCode(tag.getFirstIndicator());
        if(e.getIndicatorCode() == tag.getFirstIndicator()) {
            // We can populate the rest
            MarcSubfield subfield = tag.getSubfield('a', 1);
            if(subfield != null) {
                setDescription(e.getDescription());
                setTargetAudienceNote(MarcWorxDataHelper.stripStandardPunctuation(subfield.getData()));
            }
            MarcSubfield src = tag.getSubfield('b', 1);
            if(src != null) {
                setSource(src.getData());
            }
        }
    }

    @XmlElement(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name="audience")
    public String getTargetAudienceNote() {
        return targetAudienceNote;
    }

    public void setTargetAudienceNote(String targetAudienceNote) {
        this.targetAudienceNote = targetAudienceNote;
        setValid(MarcWorxStringHelper.isNotEmpty(targetAudienceNote));
    }

    @XmlElement(name="source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    
}
