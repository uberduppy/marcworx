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
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="StudyProgram")
public class StudyProgramNote extends BaseTagDataElement implements Serializable {
    private String programName;
    private String interestLevel;
    private String readingLevel;
    private String titlePointValue;
    private String institution;
    private List<ListedDataEntry> publicNote = new ArrayList<ListedDataEntry>();
    
    public StudyProgramNote() {}
    
    public StudyProgramNote(MarcTag tag) {
        parseTag(tag);
    }
    
    private void parseTag(MarcTag tag) {
        MarcSubfield subfield = tag.getSubfield('a', 1);
        if(subfield != null) {
            setProgramName(subfield.getDataUnpunctuated());
            MarcSubfield workSub = tag.getSubfield('b', 1);
            if(workSub != null) {
                setInterestLevel(workSub.getDataUnpunctuated());
            }
            workSub = tag.getSubfield('c', 1);
            if(workSub != null) {
                setReadingLevel(workSub.getDataUnpunctuated());
            }
            workSub = tag.getSubfield('d', 1);
            if(workSub != null) {
                setTitlePointValue(workSub.getDataUnpunctuated());
            }
            workSub = tag.getSubfield('5', 1);
            if(workSub != null) {
                setInstitution(workSub.getDataUnpunctuated());
            }
            List<MarcSubfield> pubNotes = tag.getSubfields('z');
            for(MarcSubfield sub : pubNotes) {
                publicNote.add(new ListedDataEntry(sub.getDataUnpunctuated()));
            }
        }

    }

    @XmlElement(name="programName")
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
        setValid(MarcWorxStringHelper.isNotEmpty(programName));
    }

    @XmlElement(name="interestLevel")
    public String getInterestLevel() {
        return interestLevel;
    }

    public void setInterestLevel(String interestLevel) {
        this.interestLevel = interestLevel;
    }

    @XmlElement(name="readingLevel")
    public String getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(String readingLevel) {
        this.readingLevel = readingLevel;
    }

    @XmlElement(name="titlePointValue")
    public String getTitlePointValue() {
        return titlePointValue;
    }

    public void setTitlePointValue(String titlePointValue) {
        this.titlePointValue = titlePointValue;
    }

    @XmlElement(name="institution")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @XmlElementWrapper(name = "publicNotes")
    @XmlElement(name="data")
    public List<ListedDataEntry> getPublicNote() {
        return publicNote;
    }

    public void setPublicNote(List<ListedDataEntry> publicNote) {
        this.publicNote = publicNote;
    }
    
    
    
}
