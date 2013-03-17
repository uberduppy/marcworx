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
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.marc.containers.MarcSubfield;

/**
 *
 * @author Todd
 */
public abstract class BaseMarcTag implements IMarcObject {
    private int tagNumber;
    private String tagData;
    private char firstIndicator;
    private char secondIndicator;
    private List<BaseMarcSubfield> subfields = new ArrayList<BaseMarcSubfield>();
    private int nextSubfieldIndex = MarcSubfieldConstants.FIRST_SUBFIELD_INDEX;
    private int tagIndex = MarcTagConstants.UNKNOWN_TAG_INDEX;

    protected BaseMarcTag() {
    }
    
    protected BaseMarcTag(int tagNumber) {
        this.tagNumber = tagNumber;
    }

    protected BaseMarcTag(int tagNumber, String tagData) {
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

    public void replaceSubfieldListInternal(List<BaseMarcSubfield> newList) {
        subfields = newList;
    }
    
    public void addOrUpdateSubfields(List<BaseMarcSubfield> subfields) {
        for (BaseMarcSubfield marcSubfield : subfields) {
            addOrUpdateSubfield(marcSubfield);
        }
    }

    public void addOrUpdateSubfield(BaseMarcSubfield subfield) {
        if(!subfield.isIndexed()) {
            // New subfield, add it
            addNewSubfield(subfield);
        } else {
            BaseMarcSubfield newSubfield = findSubfieldByIndex(subfield.getSubfieldIndex());
            if(newSubfield != null) {
                // Found the subfield i'm supposed to use, add it.
                newSubfield.setCode(subfield.getCode());
                newSubfield.setData(subfield.getData());
            } else {
                addNewSubfield(subfield);
            }
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

    public List<? extends BaseMarcSubfield> getSubfields() {
        return subfields;
    }
    
    protected void setSubfields(List<BaseMarcSubfield> subfields) {
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

    protected void addNewSubfield(BaseMarcSubfield subfield) {
        MarcSubfield newSubfield = cloneSubfield(subfield);
        nextSubfieldIndex++;
        newSubfield.setSubfieldIndex(nextSubfieldIndex);
        subfields.add(newSubfield);
    }
    
    public <T extends BaseMarcSubfield> T findSubfieldByIndex(int subfieldIndex) {
        BaseMarcSubfield result = null;
        for (BaseMarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getSubfieldIndex() == subfieldIndex) {
                result = marcSubfield;
                break;
            }
        }
        return (T)result;
    }
    
    public String getCurrentTagData() {
        if(tagNumber < 10) {
            return tagData;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstIndicator());
        sb.append(getSecondIndicator());
        for (BaseMarcSubfield marcSubfield : subfields) {
            sb.append(MarcSubfieldConstants.SUBFIELD_CODE);
            sb.append(marcSubfield.getCode());
            sb.append(marcSubfield.getData());

        }
        return sb.toString();

    }

    public List<? extends BaseMarcSubfield> getSubfields(char code) {
        List<BaseMarcSubfield> returnList = new ArrayList<BaseMarcSubfield>();
        for (BaseMarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getCode() == code) {
                returnList.add(marcSubfield);
            }
        }
        return returnList;
    }
    
    public <T extends BaseMarcSubfield> T getSubfield(char subLetter, int occurrenceOneBased) {
        BaseMarcSubfield returnData = null;
        int count = 0;
        for (BaseMarcSubfield marcSubfield : subfields) {
            if(marcSubfield.getCode() == subLetter) {
                count++;
                if(count == occurrenceOneBased) {
                    returnData = marcSubfield;
                    break;
                }
            }
        }
        return (T)returnData;

    }
    
    public String getSubfieldData(String selectedSubs, String delimiter) {
        StringBuilder result = new StringBuilder();
        for(BaseMarcSubfield marcSubfield : getSubfields()) {
            if(MarcWorxStringHelper.contains(selectedSubs, marcSubfield.getCode())) {
                if(result.length() > 0 && MarcWorxStringHelper.isNotEmpty(marcSubfield.getData())) {
                    result.append(delimiter);
                }
                result.append(marcSubfield.getData());
            }
        }
        
        return result.toString();
    }
    
    
    public abstract List<? extends BaseMarcSubfield> createSubfieldList();
    public abstract <T extends BaseMarcSubfield> T cloneSubfield(BaseMarcSubfield subfield);
}
