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
package org.talwood.marcworx.marc.iface;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;

/**
 *
 * @author Todd
 */
public abstract class BaseMarcRecord implements IMarcObject {

    private BaseMarcLeader leader = null;
    private List<BaseMarcTag> tags = new ArrayList<BaseMarcTag>();
    private MarcFileReadStatus readStatus = MarcFileReadStatus.VALID;
    private int nextTagIndex = MarcTagConstants.FIRST_TAG_INDEX;
    
    public BaseMarcRecord(char recordType) throws MarcException {
        preConstruction();
        leader = new MarcLeader(recordType);
        tags = new ArrayList<BaseMarcTag>();
        postConstruction();
    }
    
    public <T extends BaseMarcLeader> T getLeader() {
        return (T)leader;
    }

    public void setLeader(BaseMarcLeader leader) {
        this.leader = leader;
    }

    public List<? extends BaseMarcTag> getTags() {
        return tags;
    }

    public void setTags(List<BaseMarcTag> tags) {
        this.tags = tags;
    }
    
    public MarcFileReadStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(MarcFileReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public <T extends BaseMarcTag> T getTag(int tagNumber, int occurrenceOneBased) {
        BaseMarcTag tag = null;
        List<BaseMarcTag> results = getAllTags(tagNumber);
        if(results.size() <= occurrenceOneBased) {
            tag = results.get(occurrenceOneBased - 1);
        }
        return (T)tag;
    }
    public List<BaseMarcTag> getAllTags(int tagNumber) {
        List<BaseMarcTag> results = new ArrayList<BaseMarcTag>();
        for(BaseMarcTag marcTag : getTags()) {
            if(marcTag.getTagNumber() == tagNumber) {
                results.add(marcTag);
            }
        }
        return results;
    }
    
    
}
