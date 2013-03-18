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
package org.talwood.marcworx.marc.containers;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;
import org.talwood.marcworx.helpers.MarcWorxRecordHelper;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;
import org.talwood.marcworx.marc.enums.RecordType;

public class MarcRecord {
    private MarcLeader leader = null;
    private List<MarcTag> tags = new ArrayList<MarcTag>();
    private MarcFileReadStatus readStatus = MarcFileReadStatus.VALID;
    private int nextTagIndex = MarcTagConstants.FIRST_TAG_INDEX;
  
    public MarcRecord(char recordType) throws MarcException {
        leader = new MarcLeader(recordType);
    }

    public static MarcTag createFixedTag(int tagNumber, String tagData) throws MarcException {
        MarcTag result = new MarcTag(tagNumber, tagData);
        return result;
    }


    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, List<MarcSubfield> subs) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        for(MarcSubfield sub : subs) {
            result.addOrUpdateSubfield(sub);
        }
        return result;
    }

    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, MarcSubfield... subs) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        for (int i = 0; i < subs.length; i++) {
            result.addOrUpdateSubfield(subs[i]);

        }
        return result;
    }

    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, char subInd, String subData) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        result.addOrUpdateSubfield(new MarcSubfield(subInd, subData));
        return result;
    }


    public FormatType getFormatType() {
        return FormatType.findByFormatType(RecordType.findByRecordTypeCode(getLeader().getRecordType(), RecordType.RECORD_TYPE_UNKNOWN).getCode());
    }

    public MarcLeader getLeader() {
        return leader;
    }

    public void setLeader(MarcLeader leader) {
        this.leader = leader;
    }

    public List<MarcTag> getTags() {
        return tags;
    }

    public void setTags(List<MarcTag> tags) {
        this.tags = tags;
    }
    
    public MarcFileReadStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(MarcFileReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public MarcTag getTag(int tagNumber, int occurrenceOneBased) {
        MarcTag tag = null;
        List<MarcTag> results = getAllTags(tagNumber);
        if(results.size() <= occurrenceOneBased) {
            tag = results.get(occurrenceOneBased - 1);
        }
        return tag;
    }
    public List<MarcTag> getAllTags(int tagNumber) {
        List<MarcTag> results = new ArrayList<MarcTag>();
        for(MarcTag marcTag : getTags()) {
            if(marcTag.getTagNumber() == tagNumber) {
                results.add(MarcWorxDataHelper.cloneTag(marcTag));
            }
        }
        return results;
    }
    
    public List<MarcTag> getAllTags(int[] tagNumbers) {
        List<MarcTag> results = new ArrayList<MarcTag>();
        for(MarcTag marcTag : getTags()) {
            if(MarcWorxObjectHelper.intInArray(tagNumbers, marcTag.getTagNumber())) {
                results.add(MarcWorxDataHelper.cloneTag(marcTag));
            }
        }
        return results;
    }

    public void addOrUpdateTags(List<MarcTag> tags) {
        for (MarcTag marcTag : tags) {
            addOrUpdateTag(marcTag);
        }
    }

    public void addOrUpdateTag(MarcTag tag) {
        if(!tag.isIndexed()) {
            // New subfield, add it
            addNewTag(tag);
        } else {
            MarcTag newTag = findTagByIndex(tag.getTagIndex());
            if(newTag != null) {
                newTag.setTagNumber(tag.getTagNumber());
                newTag.setFirstIndicator(tag.getFirstIndicator());
                newTag.setSecondIndicator(tag.getSecondIndicator());
                newTag.replaceSubfieldListInternal(tag.getSubfields());
            } else {
                addNewTag(tag);
            }
        }
    }

    private void addNewTag(MarcTag tag) {
        MarcTag newTag = MarcWorxDataHelper.cloneTag(tag);
        nextTagIndex++;
        newTag.setTagIndex(nextTagIndex);
        tags.add(newTag);
    }

    public MarcTag findTagByIndex(int tagIndex) {
        MarcTag result = null;
        for (MarcTag marcTag : tags) {
            if(marcTag.getTagIndex() == tagIndex) {
                result = marcTag;
                break;
            }
        }
        return result;
    }
}
