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

package org.talwood.marcworx.innovation;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.exception.ConstraintExceptionType;
import org.talwood.marcworx.helpers.TupleContainer;
import org.talwood.marcworx.innovation.containers.StudyProgramTransformerElement;
import org.talwood.marcworx.innovation.containers.TargetAudienceTransformerElement;
import org.talwood.marcworx.innovation.containers.TitleTransformerElement;
import org.talwood.marcworx.innovation.xformers.StudyProgramTransformer;
import org.talwood.marcworx.innovation.xformers.TargetAudienceTransformer;
import org.talwood.marcworx.innovation.xformers.TitleTransformer;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.xform.XMLFormatter;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="marcdata")
public class MarcRecordTransformer implements Serializable {

    private TitleTransformerElement title;
//    private ControlNumberTransformer controlNumbers;
    private List<TargetAudienceTransformerElement> targetAudience = new ArrayList<TargetAudienceTransformerElement>();
    private List<StudyProgramTransformerElement> studyProgram = new ArrayList<StudyProgramTransformerElement>();
    private List<TupleContainer<ConstraintExceptionType, String>> processingErrors = new ArrayList<TupleContainer<ConstraintExceptionType, String>>();
    
    private MarcRecordTransformer() {}
    
    public MarcRecordTransformer(MarcRecord record) {
        parseData(record);
    }

    private void parseData(MarcRecord record) {
        // Process control numbers
//        controlNumbers = new ControlNumberTransformer(record);
        // Let's get the 245 going.
        processTitle(record.getTag(245, 1));
        processTargetAudience(record.getAllTags(521));
        processStudyProgram(record.getAllTags(526));
    }
    
    private void processStudyProgram(List<MarcTag> tags) {
        for(MarcTag tag : tags) {
            StudyProgramTransformer tat = new StudyProgramTransformer(tag);
            try {
                StudyProgramTransformerElement thisElement = tat.processTagData();
                if(thisElement.checkValidity()) {
                    studyProgram.add(thisElement);
                }
            } catch (ConstraintException ex) {
                handleException(ex);
            }
        }
        
    }
    private void processTargetAudience(List<MarcTag> tags) {
        for(MarcTag tag : tags) {
            TargetAudienceTransformer tat = new TargetAudienceTransformer(tag);
            try {
                TargetAudienceTransformerElement thisElement = tat.processTagData();
                if(thisElement.checkValidity()) {
                    targetAudience.add(thisElement);
                }
            } catch (ConstraintException ex) {
                handleException(ex);
            }
        }
    }
    
    private void processTitle(MarcTag tag245) {
        if(tag245 != null) {
            TitleTransformer tt = new TitleTransformer(tag245);
            try {
                TitleTransformerElement thisTitle = tt.processTagData();
                if(thisTitle.checkValidity()) {
                    setTitle(thisTitle);
                }
            } catch (ConstraintException ex) {
                handleException(ex);
            }
        }
    }
    
    private void handleException(ConstraintException ex) {
        processingErrors.add(new TupleContainer<ConstraintExceptionType, String>(ex.getType(), ex.getLocalizedMessage())) ;
    }

    public String toXml() throws Exception {
        JAXBContext context = JAXBContext.newInstance(MarcRecordTransformer.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(this, sw);
        XMLFormatter xmlf = new XMLFormatter();
        return xmlf.format(sw.toString());
    }

    public boolean hasProcessingErrors() {
        return (processingErrors.size() > 0);
    }
    
    @XmlElement(name="Title")
    public TitleTransformerElement getTitle() {
        return title;
    }

    public void setTitle(TitleTransformerElement title) {
        this.title = title;
    }

    @XmlElement(name="TargetAudience")
    public List<TargetAudienceTransformerElement> getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(List<TargetAudienceTransformerElement> targetAudience) {
        this.targetAudience = targetAudience;
    }

    public List<TupleContainer<ConstraintExceptionType, String>> getProcessingErrors() {
        return processingErrors;
    }

    protected void setProcessingErrors(List<TupleContainer<ConstraintExceptionType, String>> processingErrors) {
        this.processingErrors = processingErrors;
    }

    @XmlElement(name="StudyProgram")
    public List<StudyProgramTransformerElement> getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(List<StudyProgramTransformerElement> studyProgram) {
        this.studyProgram = studyProgram;
    }


    
}
