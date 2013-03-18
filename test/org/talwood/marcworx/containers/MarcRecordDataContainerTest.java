/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.containers;

import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.containers.elements.ListedDataEntry;
import org.talwood.marcworx.containers.elements.PublicationInformation;
import org.talwood.marcworx.containers.elements.StudyProgramNote;
import org.talwood.marcworx.containers.elements.TargetAudienceNote;
import org.talwood.marcworx.helpers.MarcWorxTestHelper;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.constants.RecordTypeConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.xform.XMLFormatter;

/**
 *
 * @author twalker
 */
public class MarcRecordDataContainerTest {
    
    public MarcRecordDataContainerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllInfo() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        MarcLeader leader = record.getLeader();
        leader.setPosition18('i');
        record.setLeader(leader);
        record.addOrUpdateTag(new MarcTag(8, "121299s1990                             "));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(40, ' ', ' ', new MarcSubfield('e', "rda")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '1', new MarcSubfield('a', "New York :"), new MarcSubfield('b', "George Publishing"), new MarcSubfield('c', "1957")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '4', new MarcSubfield('c', "1995")));
        record.addOrUpdateTag(new MarcTag(521, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "a3.1." + MarcSubfieldConstants.SUBFIELD_CODE + "bFollett"));
        record.addOrUpdateTag(new MarcTag(521, "3 " + MarcSubfieldConstants.SUBFIELD_CODE + "a[Green,.]" + MarcSubfieldConstants.SUBFIELD_CODE + "bSpecial"));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aProgramName" + MarcSubfieldConstants.SUBFIELD_CODE + "b5-10." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOLLY" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 1" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 2" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 3" ));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aAnotherName" + MarcSubfieldConstants.SUBFIELD_CODE + "b8-12." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOL" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 4" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 5" ));
        MarcRecordDataContainer instance = new MarcRecordDataContainer(record);
        
//        JAXBContext context = JAXBContext.newInstance(MarcRecordDataContainer.class);
//        Marshaller marshaller = context.createMarshaller();
//        StringWriter sw = new StringWriter();
//        marshaller.marshal(marshaller, sw);
//        XMLFormatter xmlf = new XMLFormatter();
//        xmlf.format(sw.toString());
//        System.out.println(xmlf.format(sw.toString()));
        System.out.println(instance.toXml());
//        result = (T) marshaller.unmarshal(new StringReader(xml));
        
        
    }
    @Test
    public void testGetPublicationInformationAACR2() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        record.addOrUpdateTag(new MarcTag(8, "121299s1990                             "));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(260, ' ', ' ', new MarcSubfield('a', "New York :"), new MarcSubfield('b', "George Publishing"), new MarcSubfield('c', "1957, c1995")));
        MarcRecordDataContainer instance = new MarcRecordDataContainer(record);

        PublicationInformation pi = instance.getPubInfo();
        assertNotNull(pi);
        assertEquals("1990", pi.getDateOfPublication());
        assertEquals("1995", pi.getDateOfCopyright());
        assertEquals("New York", pi.getPlaceOfPublication());
        assertEquals("George Publishing", pi.getNameOfPublisher());
    }
    @Test
    public void testGetPublicationInformationRDA() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        MarcLeader leader = record.getLeader();
        leader.setPosition18('i');
        record.setLeader(leader);
        record.addOrUpdateTag(new MarcTag(8, "121299s1990                             "));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(40, ' ', ' ', new MarcSubfield('e', "rda")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '1', new MarcSubfield('a', "New York :"), new MarcSubfield('b', "George Publishing"), new MarcSubfield('c', "1957")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '4', new MarcSubfield('c', "1995")));
        MarcRecordDataContainer instance = new MarcRecordDataContainer(record);

        PublicationInformation pi = instance.getPubInfo();
        assertNotNull(pi);
        assertEquals("1957", pi.getDateOfPublication());
        assertEquals("1995", pi.getDateOfCopyright());
        assertEquals("New York", pi.getPlaceOfPublication());
        assertEquals("George Publishing", pi.getNameOfPublisher());
    }
    /**
     * Test of getTargetAudience method, of class MarcRecordDataContainer.
     */
    @Test
    public void testGetTargetAudience() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        record.addOrUpdateTag(new MarcTag(521, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "a3.1." + MarcSubfieldConstants.SUBFIELD_CODE + "bFollett"));
        record.addOrUpdateTag(new MarcTag(521, "3 " + MarcSubfieldConstants.SUBFIELD_CODE + "a[Green,.]" + MarcSubfieldConstants.SUBFIELD_CODE + "bSpecial"));
        MarcRecordDataContainer instance = new MarcRecordDataContainer(record);
        List<TargetAudienceNote> notes = instance.getTargetAudience();
        assertNotNull(notes);
        assertEquals(2, notes.size());
        TargetAudienceNote note = notes.get(0);
        assertTrue(note.isValid());
        assertEquals("3.1", note.getTargetAudienceNote());
        assertEquals("Follett", note.getSource());
        assertEquals("Reading grade level", note.getDescription());
            
        note = notes.get(1);
        assertTrue(note.isValid());
        assertEquals("Green", note.getTargetAudienceNote());
        assertEquals("Special", note.getSource());
        assertEquals("Special audience", note.getDescription());
            
    }

    /**
     * Test of getStudyPrograms method, of class MarcRecordDataContainer.
     */
    @Test
    public void testGetStudyPrograms() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aProgramName" + MarcSubfieldConstants.SUBFIELD_CODE + "b5-10." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOLLY" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 1" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 2" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 3" ));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aAnotherName" + MarcSubfieldConstants.SUBFIELD_CODE + "b8-12." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOL" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 4" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 5" ));
        MarcRecordDataContainer instance = new MarcRecordDataContainer(record);
        List<StudyProgramNote> notes = instance.getStudyPrograms();
        assertNotNull(notes);
        assertEquals(2, notes.size());
        
        StudyProgramNote note = notes.get(0);
        assertTrue(note.isValid());
        assertEquals("FOLLY", note.getInstitution());
        assertEquals("5-10", note.getInterestLevel());
        assertEquals("ProgramName", note.getProgramName());
        assertEquals("6.0", note.getReadingLevel());
        assertEquals("75", note.getTitlePointValue());
        List<ListedDataEntry> pubNotes = note.getPublicNote();
        assertNotNull(pubNotes);
        assertEquals(3, pubNotes.size());
        assertEquals("Note 1", pubNotes.get(0).getData());
        assertEquals("Note 2", pubNotes.get(1).getData());
        assertEquals("Note 3", pubNotes.get(2).getData());
        
            
        note = notes.get(1);
        assertTrue(note.isValid());
        assertEquals("FOL", note.getInstitution());
        assertEquals("8-12", note.getInterestLevel());
        assertEquals("AnotherName", note.getProgramName());
        assertEquals("6.0", note.getReadingLevel());
        assertEquals("75", note.getTitlePointValue());
        pubNotes = note.getPublicNote();
        assertNotNull(pubNotes);
        assertEquals(2, pubNotes.size());
        assertEquals("Note 4", pubNotes.get(0).getData());
        assertEquals("Note 5", pubNotes.get(1).getData());
    }

}