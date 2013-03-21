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

package org.talwood.marcworx.innovation.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.innovation.annotation.DataTransformerField;
import org.talwood.marcworx.innovation.annotation.NonRepeatableFieldString;
import org.talwood.marcworx.innovation.containers.listobjects.DataTransformerElement;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="StudyProgram")
public class StudyProgramTransformerElement extends BaseTransformerElement implements Serializable {

    @NonRepeatableFieldString(subfield='a') private String programName;
    @NonRepeatableFieldString(subfield='b') private String interestLevel;
    @NonRepeatableFieldString(subfield='c') private String readingLevel;
    @NonRepeatableFieldString(subfield='d') private String titlePointValue;
    @NonRepeatableFieldString(subfield='i') private String displayText;
    @NonRepeatableFieldString(subfield='5') private String institution;
    @DataTransformerField(subfield='z') private List<DataTransformerElement> publicNote = new ArrayList<DataTransformerElement>();
    
    public StudyProgramTransformerElement() {
        
    }

    @XmlElement(name="ProgramName")
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
        setValid(MarcWorxStringHelper.isNotEmpty(programName));
    }

    @XmlElement(name="InterestLevel")
    public String getInterestLevel() {
        return interestLevel;
    }

    public void setInterestLevel(String interestLevel) {
        this.interestLevel = interestLevel;
    }

    @XmlElement(name="ReadingLevel")
    public String getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(String readingLevel) {
        this.readingLevel = readingLevel;
    }

    @XmlElement(name="TitlePointValue")
    public String getTitlePointValue() {
        return titlePointValue;
    }

    public void setTitlePointValue(String titlePointValue) {
        this.titlePointValue = titlePointValue;
    }

    @XmlElement(name="DisplayText")
    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    @XmlElement(name="Institution")
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @XmlElementWrapper(name = "PublicNotes")
    @XmlElement(name="data")
    public List<DataTransformerElement> getPublicNote() {
        return publicNote;
    }

    public void setPublicNote(List<DataTransformerElement> publicNote) {
        this.publicNote = publicNote;
    }
    
    
    
}
