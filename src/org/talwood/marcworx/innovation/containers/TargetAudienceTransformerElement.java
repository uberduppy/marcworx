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

package org.talwood.marcworx.innovation.containers;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.innovation.annotation.NonRepeatableFieldString;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="TargetAudience")
public class TargetAudienceTransformerElement extends BaseTransformerElement implements Serializable {

    private String description; // Handled by Transformer
    @NonRepeatableFieldString(subfield = 'a') private String targetAudienceNote;
    @NonRepeatableFieldString(subfield = 'b') private String source;
    
    public TargetAudienceTransformerElement() {
        
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
