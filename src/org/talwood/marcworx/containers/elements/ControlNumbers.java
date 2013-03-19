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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.DataWorkConstants;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.stdnumbers.IntStdBookNumber;
import org.talwood.marcworx.marc.stdnumbers.IntStdSerialNumber;
import org.talwood.marcworx.marc.stdnumbers.LibCongressControlNumber;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="ControlNumbers")
public class ControlNumbers extends BaseTagDataElement implements Serializable {

    private IndexedDisplayDataEntry lccn;
    private List<IndexedDisplayDataEntry> isbns = new ArrayList<IndexedDisplayDataEntry>();
    private List<IndexedDisplayDataEntry> issns = new ArrayList<IndexedDisplayDataEntry>();
    
    public ControlNumbers() {
        
    }
    
    public ControlNumbers(List<MarcTag> tags) {
        parseTags(tags);
    }

    private void parseTags(List<MarcTag> tags) {
        for(MarcTag tag : tags) {
            String subData = null;
            switch(tag.getTagNumber()) {
                case 10:
                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
                        LibCongressControlNumber libCtrl = new LibCongressControlNumber(subData);
                        if(libCtrl.isValid()) {
                            lccn = new IndexedDisplayDataEntry(libCtrl.getDisplayable(), libCtrl.getSortable());
                        }
                    }
                    break;
                case 20:
                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
                        IntStdBookNumber isbnCtrl = new IntStdBookNumber(subData);
                        if(isbnCtrl.isValid()) {
                            isbns.add(new IndexedDisplayDataEntry(isbnCtrl.getDisplayable(), isbnCtrl.getSortable()));
                        }
                    }
                    break;
                case 22:
                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
                        IntStdSerialNumber issnCtrl = new IntStdSerialNumber(subData);
                        if(issnCtrl.isValid()) {
                            issns.add(new IndexedDisplayDataEntry(issnCtrl.getDisplayable(), issnCtrl.getSortable()));
                        }
                    }
                    break;
                default:
                    // Don't process any that are not covered above
                    break;
            }
        }
    }

    @XmlElement(name="LCCN")
    public IndexedDisplayDataEntry getLccn() {
        return lccn;
    }

    public void setLccn(IndexedDisplayDataEntry lccn) {
        this.lccn = lccn;
    }

    @XmlElement(name="ISBN")
    public List<IndexedDisplayDataEntry> getIsbns() {
        return isbns;
    }

    public void setIsbns(List<IndexedDisplayDataEntry> isbns) {
        this.isbns = isbns;
    }

    @XmlElement(name="ISSN")
    public List<IndexedDisplayDataEntry> getIssns() {
        return issns;
    }

    public void setIssns(List<IndexedDisplayDataEntry> issns) {
        this.issns = issns;
    }
    
    
}
