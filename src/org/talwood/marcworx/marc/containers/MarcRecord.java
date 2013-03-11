package org.talwood.marcworx.marc.containers;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;
import org.talwood.marcworx.marc.enums.RecordType;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;

public class MarcRecord {

    private MarcLeader leader = null;
    private List<MarcTag> tags = null;
    private MarcFileReadStatus readStatus = MarcFileReadStatus.VALID;
    private int nextTagIndex = MarcTagConstants.FIRST_TAG_INDEX;
  
    private MarcRecord() {}

    public MarcRecord(RecordType recordType) throws MarcException {
        leader = new MarcLeader(recordType);
        tags = new ArrayList<MarcTag>();
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
                results.add(marcTag);
            }
        }
        return results;
    }
    
    public List<MarcTag> getAllTags(int[] tagNumbers) {
        List<MarcTag> results = new ArrayList<MarcTag>();
        for(MarcTag marcTag : getTags()) {
            if(MarcWorxObjectHelper.intInArray(tagNumbers, marcTag.getTagNumber())) {
                results.add(marcTag);
            }
        }
        return results;
    }

    public static MarcTag createFixedTag(int tagNumber, String tagData) throws MarcException {
        MarcTag result = new MarcTag(tagNumber, tagData);
        return result;
    }


    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, List<MarcSubfield> subs) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        result.addOrUpdateSubfields(subs);
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

    public FormatType getFormatType() {
        return leader.getFormatTypeEnum();
    }
}
