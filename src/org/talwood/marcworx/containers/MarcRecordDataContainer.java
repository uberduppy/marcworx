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

package org.talwood.marcworx.containers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.containers.elements.StudyProgramNote;
import org.talwood.marcworx.containers.elements.TargetAudienceNote;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
/**
 *
 * @author twalker
 */
@XmlRootElement(name="marcdata")
public class MarcRecordDataContainer {
    
    // Initial offering of data
    private List<TargetAudienceNote> targetAudience = new ArrayList<TargetAudienceNote>();
    private List<StudyProgramNote> studyPrograms = new ArrayList<StudyProgramNote>();
    
    
    private MarcRecordDataContainer() {}
    
    public MarcRecordDataContainer(MarcRecord record) {
        parseData(record);
    }
    
    private void parseData(MarcRecord record) {
        // Parse title entries
        
        // Parse name entries
        
        // Parse publication info
        
        // Parse target audience
        for(MarcTag tag521 : record.getAllTags(521)) {
            TargetAudienceNote note = new TargetAudienceNote(tag521);
            if(note.isValid()) {
                targetAudience.add(note);
            }
        }
        // Parse reading programs
        for(MarcTag tag526 : record.getAllTags(526)) {
            StudyProgramNote spn = new StudyProgramNote(tag526);
            if(spn.isValid()) {
                studyPrograms.add(spn);
            }
        }
        
    }

    @XmlElement(name="TargetAudience")
    public List<TargetAudienceNote> getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(List<TargetAudienceNote> targetAudience) {
        this.targetAudience = targetAudience;
    }

    @XmlElement(name="StudyProgram")
    public List<StudyProgramNote> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<StudyProgramNote> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }

    
    
}
