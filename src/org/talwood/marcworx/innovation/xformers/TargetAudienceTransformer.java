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

package org.talwood.marcworx.innovation.xformers;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.containers.enums.TargetAudienceEnum;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.innovation.containers.BaseTransformerElement;
import org.talwood.marcworx.innovation.containers.TargetAudienceTransformerElement;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="TargetAudience")
public class TargetAudienceTransformer extends BaseTransformer implements Serializable {
    private static final long serialVersionUID = 1L;

    
    public TargetAudienceTransformer(MarcTag tag) {
        super(tag);
    }
    
    @Override
    public BaseTransformerElement buildElement() {
        return new TargetAudienceTransformerElement();
    }

    @Override
    public void doSpecialProcessing(BaseTransformerElement element) {
        // No special processing required for title tags
        if(element instanceof TargetAudienceTransformerElement) {
            TargetAudienceTransformerElement tate = (TargetAudienceTransformerElement) element;
            TargetAudienceEnum e = TargetAudienceEnum.findByIndicatorCode(tag.getFirstIndicator());
            if(e.getIndicatorCode() == tag.getFirstIndicator()) {
                MarcSubfield subfield = tag.getSubfield('a', 1);
                if(subfield != null) {
                    tate.setDescription(e.getDescription());
                    tate.setValid(true);
                }
            }
        }
    }

    
}
