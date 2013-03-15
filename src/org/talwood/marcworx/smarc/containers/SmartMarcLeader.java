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

import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.marc.enums.RecordType;

/**
 *
 * @author twalker
 */
public class SmartMarcLeader extends MarcLeader {

    private RecordType recordType = RecordType.RECORD_TYPE_UNKNOWN;
    
    public SmartMarcLeader(MarcLeader leader) {
        super(leader.getCurrentLeaderData());
    }
    
    public SmartMarcLeader(String leaderData) {
        super(leaderData);
        recordType = RecordType.findByRecordTypeCode(getPosition05(), RecordType.RECORD_TYPE_UNKNOWN);
    }
    
    public RecordType getRecordTypeEnum() {
        return recordType;
    }
    
    public FormatType getFormatTypeEnum() {
        return recordType.getFormatType();
    }
    
    
}
