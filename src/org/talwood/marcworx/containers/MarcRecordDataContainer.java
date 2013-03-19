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

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.containers.elements.ControlNumbers;
import org.talwood.marcworx.containers.elements.MainEntryElement;
import org.talwood.marcworx.containers.elements.PublicationInformation;
import org.talwood.marcworx.containers.elements.StudyProgramNote;
import org.talwood.marcworx.containers.elements.TargetAudienceNote;
import org.talwood.marcworx.containers.elements.TitleElement;
import org.talwood.marcworx.helpers.MarcWorxRecordHelper;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.xform.XMLFormatter;
/**
 *
 * @author twalker
 */
@XmlRootElement(name="marcdata")
public class MarcRecordDataContainer implements Serializable {
    
    // Initial offering of data
    private List<TargetAudienceNote> targetAudience = new ArrayList<TargetAudienceNote>();
    private List<StudyProgramNote> studyPrograms = new ArrayList<StudyProgramNote>();
    private List<MainEntryElement> mainEntries = new ArrayList<MainEntryElement>();
    private PublicationInformation pubInfo;
    private TitleElement titleElement;
    private ControlNumbers controlNumbers;

    
    private MarcRecordDataContainer() {}
    
    public MarcRecordDataContainer(MarcRecord record) {
        parseData(record);
    }
    
    private void parseData(MarcRecord record) {
        // Parse control entries
        List<MarcTag> controlTags = record.getAllTagsInRange(10, 99);
        controlNumbers = new ControlNumbers(controlTags);
        
        // Parse title entries
        MarcTag tag245 = record.getTag(245, 1);
        if(tag245 != null) {
            titleElement = new TitleElement(tag245);
        }
        // Parse name entries
        List<MarcTag> mainEntryTags = record.getAllTags(new int[]{100, 110, 111, 130});
        for(MarcTag mainEntry : mainEntryTags) {
            MainEntryElement mee = new MainEntryElement(mainEntry);
            if(mee.isValid()) {
                mainEntries.add(mee);
            }
        }
        
        // Parse publication info
        List<MarcTag> pubTags = new ArrayList<MarcTag>();
        if(MarcWorxRecordHelper.doesRecordConformToRda(record)) {
            pubTags.addAll(record.getAllTags(new int[]{264, 8, 260}));
        } else {
            pubTags.addAll(record.getAllTags(new int[]{8, 260}));
        }
        pubInfo = new PublicationInformation(pubTags);
        
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

    @XmlElement(name="PublicationInfo")
    public PublicationInformation getPubInfo() {
        return pubInfo;
    }

    public void setPubInfo(PublicationInformation pubInfo) {
        this.pubInfo = pubInfo;
    }

    @XmlElement(name="ControlNumbers")
    public ControlNumbers getControlNumbers() {
        return controlNumbers;
    }

    public void setControlNumbers(ControlNumbers controlNumbers) {
        this.controlNumbers = controlNumbers;
    }

    @XmlElement(name="MainEntry")
    public List<MainEntryElement> getMainEntries() {
        return mainEntries;
    }

    public void setMainEntries(List<MainEntryElement> mainEntries) {
        this.mainEntries = mainEntries;
    }

    @XmlElement(name="Title")
    public TitleElement getTitleElement() {
        return titleElement;
    }

    public void setTitleElement(TitleElement titleElement) {
        this.titleElement = titleElement;
    }
    
    
    
    
    public String toXml() throws Exception {
        JAXBContext context = JAXBContext.newInstance(MarcRecordDataContainer.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(this, sw);
        XMLFormatter xmlf = new XMLFormatter();
        return xmlf.format(sw.toString());
    }
    
}
