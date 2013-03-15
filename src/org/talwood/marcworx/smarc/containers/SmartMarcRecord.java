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

package org.talwood.marcworx.smarc.containers;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.smarc.helpers.SmartMarcHelper;

/**
 * This class is the outer representation of a MARC record that is intelligent
 * about it's contents, as opposed to the more generic MarcRecord class, which
 * only knows the rules of MARC structure.
 * All data elements are constructed from, but not necessarily derived from
 * The standard MARC classes
 * @author twalker
 */
public class SmartMarcRecord {
    
    private SmartMarcLeader leader;
    private List<SmartMarcBaseTag> tags = new ArrayList<SmartMarcBaseTag>();
    
    private SmartMarcRecord() {} // Block creation of empty smart record.
    
    public SmartMarcRecord(MarcRecord record) {
        leader = new SmartMarcLeader(record.getLeader());
        for(MarcTag tag : record.getTags()) {
            tags.add(SmartMarcHelper.buildSmartTag(tag));
        }
    }
}
