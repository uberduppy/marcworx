package org.talwood.marcworx.marc.containers;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

public class MarcTag {
    private int tagNumber;
    private String tagData;
    private char firstIndicator;
    private char secondIndicator;
    private List<MarcSubfield> subfields = new ArrayList<MarcSubfield>();
    private int nextSubfieldIndex = MarcSubfieldConstants.FIRST_SUBFIELD_INDEX;
    
    private int tagIndex = MarcTagConstants.UNKNOWN_TAG_INDEX;
    
    public MarcTag(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    public MarcTag(int tagNumber, String tagData) {
        this.tagNumber = tagNumber;
        this.tagData = tagData;
        if(tagNumber > 9) {
            buildTagFromStringData(tagData);
        }
    }

    private void buildTagFromStringData(String tagData) {
        if(!MarcWorxStringHelper.hasAtLeastNCharacters(tagData, 4)) {
            tagData = "  " + MarcSubfieldConstants.SUBFIELD_CODE + "a";
        }
        firstIndicator = tagData.charAt(0);
        secondIndicator = tagData.charAt(1);
        char lastSubCode = ' ';
        StringBuffer lastSubfield = new StringBuffer();
        for (int i = 2; i < tagData.length(); i++) {
            char thisCharacter = tagData.charAt(i);
            if(thisCharacter == MarcSubfieldConstants.SUBFIELD_CODE) {
                if(lastSubCode != ' ') {
                    addOrUpdateSubfield(new MarcSubfield(lastSubCode, lastSubfield.toString()));
                    lastSubCode = ' ';
                    lastSubfield = new StringBuffer();
                }
            } else {
                if(lastSubCode == ' ') {
                    lastSubCode = thisCharacter;
                } else {
                    lastSubfield.append(thisCharacter);
                }
            }
        }
        if(lastSubCode != ' ') {
            addOrUpdateSubfield(new MarcSubfield(lastSubCode, lastSubfield.toString()));
        }
    }
    
    public int getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getTagData() {
        return tagData;
    }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    public char getFirstIndicator() {
        return firstIndicator;
    }

    public void setFirstIndicator(char firstIndicator) {
        this.firstIndicator = firstIndicator;
    }

    public char getSecondIndicator() {
        return secondIndicator;
    }

    public void setSecondIndicator(char secondIndicator) {
        this.secondIndicator = secondIndicator;
    }

    public List<MarcSubfield> getSubfields() {
        return subfields;
    }

    public void setSubfields(List<MarcSubfield> subfields) {
        this.subfields = subfields;
    }

    public int getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(int tagIndex) {
        this.tagIndex = tagIndex;
    }
    
    public boolean isIndexed() {
        return tagIndex != MarcTagConstants.UNKNOWN_TAG_INDEX;
    }

    public void replaceSubfieldListInternal(List<MarcSubfield> newList) {
        subfields = newList;
    }
    
    public void addOrUpdateSubfields(List<MarcSubfield> subfields) {
        for (MarcSubfield marcSubfield : subfields) {
            addOrUpdateSubfield(marcSubfield);
        }
    }

    public void addOrUpdateSubfield(MarcSubfield subfield) {
        if(!subfield.isIndexed()) {
            // New subfield, add it
            addNewSubfield(subfield);
        } else {
            MarcSubfield newSubfield = findSubfieldByIndex(subfield.getSubfieldIndex());
            if(newSubfield != null) {
                // Found the subfield i'm supposed to use, add it.
                newSubfield.setCode(subfield.getCode());
                newSubfield.setData(subfield.getData());
            } else {
                addNewSubfield(subfield);
            }
        }
    }
    
    private void addNewSubfield(MarcSubfield subfield) {
        MarcSubfield newSubfield = MarcWorxDataHelper.cloneSubfield(subfield);
        nextSubfieldIndex++;
        newSubfield.setSubfieldIndex(nextSubfieldIndex);
        subfields.add(newSubfield);
    }

    public MarcSubfield findSubfieldByIndex(int subfieldIndex) {
        MarcSubfield result = null;
        for (MarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getSubfieldIndex() == subfieldIndex) {
                result = marcSubfield;
                break;
            }
        }
        return result;
    }

    public String getCurrentTagData() {
        if(tagNumber < 10) {
            return tagData;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstIndicator());
        sb.append(getSecondIndicator());
        for (MarcSubfield marcSubfield : subfields) {
            sb.append(MarcSubfieldConstants.SUBFIELD_CODE);
            sb.append(marcSubfield.getCode());
            sb.append(marcSubfield.getData());

        }
        return sb.toString();

    }

    public List<MarcSubfield> getSubfields(char code) {
        List<MarcSubfield> returnList = new ArrayList<MarcSubfield>();
        for (MarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getCode() == code) {
                returnList.add(marcSubfield);
            }
        }
        return returnList;
    }

    public MarcSubfield getSubfield(char subLetter, int occurrenceOneBased) {
        MarcSubfield returnData = null;
        int count = 0;
        for (MarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getCode() == subLetter) {
                count++;
                if(count == occurrenceOneBased) {
                    returnData = marcSubfield;
                    break;
                }
            }
        }
        return returnData;

    }
    
    public String getSubfieldData(String selectedSubs, String delimiter) {
        StringBuilder result = new StringBuilder();
        for(MarcSubfield marcSubfield : getSubfields()) {
            if(MarcWorxStringHelper.contains(selectedSubs, marcSubfield.getCode())) {
                if(result.length() > 0 && MarcWorxStringHelper.isNotEmpty(marcSubfield.getData())) {
                    result.append(delimiter);
                }
                result.append(marcSubfield.getData());
            }
        }
        
        return result.toString();
    }
    
}
