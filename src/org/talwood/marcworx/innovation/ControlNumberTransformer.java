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

package org.talwood.marcworx.innovation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.innovation.containers.listobjects.IndexedDisplayElement;
import org.talwood.marcworx.marc.constants.DataWorkConstants;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.stdnumbers.IntStdBookNumber;
import org.talwood.marcworx.marc.stdnumbers.IntStdSerialNumber;
import org.talwood.marcworx.marc.stdnumbers.LibCongressControlNumber;

/**
 *
 * @author twalker
 */
@XmlRootElement
public class ControlNumberTransformer implements Serializable {
    
//    private IndexedDisplayElement lccn;
//    private List<IndexedDisplayElement> isbns = new ArrayList<IndexedDisplayElement>();
//    private List<IndexedDisplayElement> issns = new ArrayList<IndexedDisplayElement>();
    
    public ControlNumberTransformer(MarcRecord record) {
        parseData(record);
    }
    
    private void parseData(MarcRecord record) {
        // This builds all control numbers into one XML element
//        for(MarcTag tag : record.getAllTagsInRange(10, 22)) {
//            String subData;
//            switch(tag.getTagNumber()) {
//                case 10:
//                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
//                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
//                        LibCongressControlNumber libCtrl = new LibCongressControlNumber(subData);
//                        if(libCtrl.isValid()) {
//                            lccn = new IndexedDisplayElement(libCtrl.getDisplayable(), libCtrl.getSortable());
//                        }
//                    }
//                    break;
//                case 20:
//                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
//                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
//                        IntStdBookNumber isbnCtrl = new IntStdBookNumber(subData);
//                        if(isbnCtrl.isValid()) {
//                            isbns.add(new IndexedDisplayElement(isbnCtrl.getDisplayable(), isbnCtrl.getSortable()));
//                        }
//                    }
//                    break;
//                case 22:
//                    subData = MarcWorxDataHelper.pullDataFromSubfield(tag, 'a', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP);
//                    if(MarcWorxStringHelper.isNotEmpty(subData)) {
//                        IntStdSerialNumber issnCtrl = new IntStdSerialNumber(subData);
//                        if(issnCtrl.isValid()) {
//                            issns.add(new IndexedDisplayElement(issnCtrl.getDisplayable(), issnCtrl.getSortable()));
//                        }
//                    }
//                    break;
//                default:
//                    // Don't process any that are not covered above
//                    break;
//            }
//        }
    }

//    @XmlElement(name="LCCN")
//    public IndexedDisplayElement getLccn() {
//        return lccn;
//    }
//
//    public void setLccn(IndexedDisplayElement lccn) {
//        this.lccn = lccn;
//    }
//
//    @XmlElement(name="ISBN")
//    public List<IndexedDisplayElement> getIsbns() {
//        return isbns;
//    }
//
//    public void setIsbns(List<IndexedDisplayElement> isbns) {
//        this.isbns = isbns;
//    }
//
//    @XmlElement(name="ISSN")
//    public List<IndexedDisplayElement> getIssns() {
//        return issns;
//    }
//
//    public void setIssns(List<IndexedDisplayElement> issns) {
//        this.issns = issns;
//    }
//    
//    
    
}
